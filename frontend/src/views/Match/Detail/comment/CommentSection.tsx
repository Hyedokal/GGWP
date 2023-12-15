import React, { useEffect, useState } from 'react';
import axios from "axios";

interface Comment {
    myPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    memo: string;
    summonerRank: string;
    // Add other fields as necessary
}

interface CommentSectionProps {
    sId: number;
    refreshKey?: number; // Optional prop to trigger a refresh
}

interface PaginationInfo {
    currentPage: number;
    totalPages: number;
}

const CommentSection: React.FC<CommentSectionProps> = ({ sId, refreshKey}) => {
    const [comments, setComments] = useState<Comment[]>([]);
    const [pagination, setPagination] = useState<PaginationInfo>({ currentPage: 0, totalPages: 0 });


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
                    <p>{comment.summonerName}: {comment.memo}</p>
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