import React, {useState} from 'react';
import { BoardListResponseDto } from "../BoardListResponseDto";
import CommentSection from "./comment/CommentSection";

interface ViewPostModalProps {
    post: BoardListResponseDto;
    onClose: () => void;
}

const ViewPostModal: React.FC<ViewPostModalProps> = ({ post, onClose }) => {

    // Form state
    const [formData, setFormData] = useState({
        sId: 30,
        myPos: "",
        qType: "",
        useMic: true,
        summonerName: "",
        tag_line: "",
        memo: ""
    });

    // Function to handle form submission
    const handleSubmit = async () => {
        try {
            const response = await fetch('http://localhost:8000/v1/comments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            // Handle the response here
            console.log('Post created successfully');
        } catch (error) {
            console.error('Error while creating post:', error);
        }
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">
            <div>
                <div className="bg-[#232a38] text-white p-4 rounded-lg w-[591px]">
                    <div className="flex justify-between items-center mb-4">
                        <h1 className="text-lg font-bold mr-2">{post.summonerName}#{post.tag_line}</h1>
                    </div>
                    <div className="border-b border-[#3a4253] py-2">
                        <div className="flex justify-between items-center">
                            <div>
                                <h2 className="text-sm">티어</h2>
                                <p className="text-sm text-[#8da9c4]">{post.rank}</p>
                            </div>
                            <div>
                                <h2 className="text-sm">이전 시즌 티어</h2>
                                <p className="text-sm text-[#8da9c4]">노데이터</p>
                            </div>
                        </div>
                    </div>
                    <div className="py-2">
                        <div className="flex justify-between items-center">
                            <div>
                                <h2 className="text-sm">포지션</h2>
                                <div>
                                    <p className="text-sm text-[#8da9c4]">{post.myPos}</p>
                                </div>
                            </div>
                            <div>
                                <h2 className="text-sm">찾는 포지션</h2>
                                <div>
                                    <p className="text-sm text-[#8da9c4]">{post.wantPos}</p>
                                </div>
                            </div>
                            <p className="text-sm bg-[#3a4253] px-2 py-1 rounded">{post.qtype}</p>
                        </div>
                    </div>
                    <div className="ml-60">
                        {post.useMic ? <p className="ml-60 text-xs text-[#8da9c4]">마이크 ON</p> : <p className="ml-60 text-xs text-[#8da9c4]">마이크 OFF</p>}
                    </div>
                    <div className="py-2">
                        <h2 className="text-sm mb-2">최근 전투 결과</h2>
                        <div className="flex items-center">
                            <div className="flex flex-col items-center">
                                <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                                <p className="text-xs text-[#8da9c4]">승리</p>
                            </div>
                            <div className="flex flex-col items-center">
                                <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                                <p className="text-xs text-[#8da9c4]">승리</p>
                            </div>
                            <div className="flex flex-col items-center">
                                <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                                <p className="text-xs text-[#8da9c4]">패배</p>
                            </div>
                        </div>
                        <div className="mb-10"></div>
                        <div className="py-4 bg-[#2b3442] rounded-lg mx-4">
                            <h2 className="text-lg mb-4 font-bold text-white">메모</h2>
                            <p className="text-sm text-white">{post.memo}</p>
                        </div>
                        <div className="mb-10"></div>
                        <CommentSection sId={post.sid} />
                        <div className="flex justify-end">
                            <button className="text-sm bg-[#3a4253] px-2 py-1 rounded mr-25" onClick={handleSubmit}>Create Post</button>

                            <button className="text-sm bg-[#3a4253] px-2 py-1 rounded" onClick={onClose}>Close</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default ViewPostModal;
