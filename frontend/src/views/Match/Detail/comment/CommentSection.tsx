import React, { useEffect, useState } from 'react';
import axios from "axios";
import UserInfoStore from "../../../../stores/userInfo.store";
import {BoardListResponseDto} from "../../BoardListResponseDto";
import {approveCommentApi, fetchCommentsApi} from "../../../../apis";

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
const handleMatchClick = async (cid: number) => {
    try {
        await approveCommentApi(cid);
        // 모달닫기
        alert("매칭이 완료되었습니다.");
    } catch (error) {
    }
};



const CommentSection: React.FC<CommentSectionProps> = ({ sId, refreshKey,post}) => {
    const [comments, setComments] = useState<Comment[]>([]);
    const [pagination, setPagination] = useState<PaginationInfo>({ currentPage: 0, totalPages: 0 });
    const userInfo = UserInfoStore(state => state.userInfo);

    useEffect(() => {
        fetchComments(sId, pagination.currentPage);
    }, [sId, pagination.currentPage, refreshKey]); // Add refreshKey as a dependency


    const fetchComments = async (sId: number, page: number) => {
        try {
            const commentPage = await fetchCommentsApi(sId, page);
            setComments(commentPage.content);
            setPagination({
                currentPage: commentPage.number,
                totalPages: commentPage.totalPages
            });
        } catch (error) {
        }
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