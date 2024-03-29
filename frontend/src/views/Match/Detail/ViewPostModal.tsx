    import React, {useEffect, useState} from 'react';
    import { BoardListResponseDto } from "../BoardListResponseDto";
    import CommentSection from "./comment/CommentSection";
    import WriteCommentModal from "./comment/WriteCommentModal";
    import './style.css';
    import axios from "axios";
    import {useCookies} from "react-cookie";
    import {fetchPersonalitiesApi, fetchProfileImageApi} from "../../../apis";

    interface ViewPostModalProps {
        post: BoardListResponseDto;
        onClose: () => void;
    }

    const ViewPostModal: React.FC<ViewPostModalProps> = ({ post, onClose }) => {
        const [refreshKey, setRefreshKey] = useState(0);
        const [personalities, setPersonalities] = useState<string[]>([]);

        const [profileImageUrl, setProfileImageUrl] = useState<string>('');

        const refreshComments = () => {
            setRefreshKey(prevKey => prevKey + 1); // Increment to trigger a refresh
        };




        useEffect(() => {
            const fetchAndSetPersonalitiesAndProfileImage = async () => {
                const lolNick = post.summonerName || undefined;
                const tag = post.tagLine || undefined;

                try {
                    // Fetching personalities
                    const fetchedPersonalities = await fetchPersonalitiesApi(lolNick, tag);
                    setPersonalities(fetchedPersonalities);

                    // Fetching profile image
                    const profileImg = await fetchProfileImageApi(lolNick, tag);
                    setProfileImageUrl(profileImg);

                } catch (error) {
                    console.error('Error fetching data:', error);
                    // Handle the error appropriately
                }
            };

            fetchAndSetPersonalitiesAndProfileImage();
        }, [post.summonerName, post.tagLine]); // Dependency array



        return (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">
                <div>
                    <div className="bg-[#232a38] text-white p-4 rounded-lg w-[591px]">
                        <div className="flex justify-between items-center mb-4">
                            <h1 className="text-lg font-bold mr-2">{post.summonerName}#{post.tagLine}</h1>

                            <td className="p-4 w-1/8" style={{ display: "flex", justifyContent: "center" }}>
                                {profileImageUrl ? (
                                    <img
                                        src={profileImageUrl}
                                        alt="User Profile"
                                        style={{ width: '60px', height: '60px', borderRadius: '50%' }}
                                    />
                                ) : (
                                    <div className="w-10 h-10 bg-gray-300 rounded-full" style={{ width: '40px', height: '40px' }}></div> // Placeholder for no image
                                )}
                            </td>

                        </div>
                        <div className="border-b border-[#3a4253] py-2">
                            <div className="flex justify-between items-center">
                                <div>
                                    <h2 className="text-sm">티어</h2>
                                    <p className="text-2xl   text-[#8da9c4]">{post.rank}</p>
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
                            <h2 className="text-sm mb-2">나의 성향 </h2>
                            <div className="flex items-center">
                                <div className="flex flex-col items-center">
                                    <p className="text-2xl text-[#8da9c4]">
                                        <p>#{personalities[0]}</p>
                                    </p>
                                </div>
                                <div className="ml-5"></div>


                                <div className="flex flex-col items-center">
                                    <p className="text-2xl text-[#8da9c4]">
                                        <p>#{personalities[1]}</p>
                                    </p>
                                </div>
                                <div className="ml-5"></div>

                                <div className="flex flex-col items-center">
                                    <p className="text-2xl text-[#8da9c4]">
                                        <p>#{personalities[2]}</p>
                                    </p>
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
                                    <WriteCommentModal sId={post.sid} wontPos={post.wantPos} qType={post.qtype} onCommentAdded={refreshComments}/>

                                <div className="ml-4">   </div>
                                <button className="text-sm bg-[#3a4253] px-2 py-1 rounded" onClick={onClose}>닫기</button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        );
    };

    export default ViewPostModal;
