    import React, {useState} from 'react';
    import { BoardListResponseDto } from "../BoardListResponseDto";
    import CommentSection from "./comment/CommentSection";
    import WriteCommentModal from "./comment/WriteCommentModal";
    import './style.css';

    interface ViewPostModalProps {
        post: BoardListResponseDto;
        onClose: () => void;
    }

    const ViewPostModal: React.FC<ViewPostModalProps> = ({ post, onClose }) => {
        const [refreshKey, setRefreshKey] = useState(0);

        const refreshComments = () => {
            setRefreshKey(prevKey => prevKey + 1); // Increment to trigger a refresh
        };

        return (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">
                <div>
                    <div className="bg-[#232a38] text-white p-4 rounded-lg w-[591px]">
                        <div className="flex justify-between items-center mb-4">
                            <h1 className="text-lg font-bold mr-2">{post.summonerName}#{post.tagLine}</h1>
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
                            <CommentSection sId={post.sid} refreshKey={refreshKey} post={post} />
                            <div className="flex justify-end" id="parent-container">
                                    <WriteCommentModal    sId={post.sid} wontPos={post.wantPos} qType={post.qtype} onCommentAdded={refreshComments}/>
                                <button className="text-sm bg-[#3a4253] px-2 py-1 rounded" onClick={onClose}>Close</button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        );
    };

    export default ViewPostModal;
