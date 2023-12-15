import React, { useEffect, useState } from 'react';
import axios from "axios";
import {UserInfo} from "../../../../types";
import UserInfoStore from "../../../../stores/userInfo.store";
import {BoardListResponseDto} from "../../BoardListResponseDto";

interface Comment {
    myPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    memo: string;
    summonerRank: string;
    cid: number;
}

interface CommentSectionProps {
    sId: number;
    refreshKey?: number; // Optional prop to trigger a refresh
    post: BoardListResponseDto;

}

interface PaginationInfo {
    currentPage: number;
    totalPages: number;
}
const handleMatchClick = (cid: number) => {
    axios.put(`http://localhost:8000/v1/squads/comment/approve/${cid}`)
        .then(response => {
            // Handle successful response
            console.log("Comment approved:", response);
        })
        .catch(error => {
            // Handle error
            console.error("Error approving comment:", error);
        });
};
const CommentSection: React.FC<CommentSectionProps> = ({ sId, refreshKey,post}) => {
    const [comments, setComments] = useState<Comment[]>([]);
    const [pagination, setPagination] = useState<PaginationInfo>({ currentPage: 0, totalPages: 0 });
    const userInfo = UserInfoStore(state => state.userInfo);

    useEffect(() => {
        fetchComments(sId, pagination.currentPage);
    }, [sId, pagination.currentPage, refreshKey]); // Add refreshKey as a dependency


    const fetchComments = (sId: number, page: number) => {
        axios.get(`http://localhost:8000/v1/squads/${sId}?page=${page}`)
            .then(response => {
                if (response.data && response.data.commentPage) {
                    setComments(response.data.commentPage.content);
                    setPagination({
                        currentPage: response.data.commentPage.number,
                        totalPages: response.data.commentPage.totalPages
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching comments:', error);
            });
    };

    const handlePageChange = (newPage: number) => {
        setPagination(prev => ({ ...prev, currentPage: newPage }));
    };

    return (
        <div className="comments-section">
            {/* Render comments */}
            {comments.map((comment, index) => (
                <div key={index}>
                    <p>이메일: {userInfo?.email} {comment.summonerName}#{comment.tagLine}: {comment.memo} </p>
                    {userInfo?.lolNickname === post.summonerName && userInfo?.tag === post.tagLine && (
                        <button onClick={() => handleMatchClick(comment.cid)}>Match</button>
                        )}
                </div>
            ))}

            {/* Pagination controls */}
            <div>
                {Array.from({ length: pagination.totalPages }, (_, index) => (
                    <button key={index} onClick={() => handlePageChange(index)} disabled={index === pagination.currentPage}>
                        Page {index + 1}
                    </button>
                ))}
            </div>

        </div>
    );
};

export default CommentSection;