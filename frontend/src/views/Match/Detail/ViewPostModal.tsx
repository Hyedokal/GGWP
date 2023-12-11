// ViewPostModal.tsx
import React from 'react';
import {BoardListResponseDto} from "../BoardListResponseDto";

interface ViewPostModalProps {
    post: BoardListResponseDto;
    onClose: () => void;
}

const ViewPostModal: React.FC<ViewPostModalProps> = ({ post, onClose }) => {
    return (
        <div className="modal-background">
            {/* Display the post details here */}
            asdsadasdasdasdasdasd
            <div>Summoner Name: {post.summonerName}</div>
            {/* ... other details ... */}
            <button onClick={onClose}>Close</button>
        </div>
    );
};

export default ViewPostModal;