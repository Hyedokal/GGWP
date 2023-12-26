    import React, { useEffect, useState } from "react";
    import UserInfoStore from "../../stores/userInfo.store";
    import Modal from "../../components/ModalWrite";
    import {BoardListResponseDto} from "./BoardListResponseDto";
    import EditPostModal from "./Edit/EditPostModal";
    import ViewPostModal from "./Detail/ViewPostModal";
    import './style.css';
    import Announce from "../../components/Announce/Announce";
    import axios from "axios";
    import {deleteSquadApi, fetchMoreSquadsApi, fetchSquadsApi, fetchSquadsApi2} from "../../apis";

    export default function Match() {
        const { userInfo } = UserInfoStore();

        const [isModalOpen, setModalOpen] = useState(false);
        const [currentList, setCurrentList] = useState<BoardListResponseDto[]>([]);
        const [currentPage] = useState(0);
        const [loadMoreSize, setLoadMoreSize] = useState(15); // Initial size for loading more items

        const openModal = () => setModalOpen(true);
        const closeModal = () => setModalOpen(false);

        const [isEditModalOpen, setEditModalOpen] = useState(false);
        const [editablePost, setEditablePost] = useState<BoardListResponseDto | null>(null);

        const [isViewModalOpen, setViewModalOpen] = useState(false);
        const [viewablePost, setViewablePost] = useState<BoardListResponseDto | null>(null);


        const loadMorePosts = async () => {
            try {
                const newSize = loadMoreSize + 5; // Increment load more size by 5
                const additionalData = await fetchMoreSquadsApi(currentPage, newSize);

                setCurrentList((prevList) => {
                    // Combine existing list with new data, avoiding duplicates
                    const existingIds = new Set(prevList.map(item => item.sid));
                    const newData = additionalData.filter((item: { sid: number; }) => !existingIds.has(item.sid));
                    return [...prevList, ...newData];
                });
                setLoadMoreSize(newSize);
            } catch (error) {
            }
        };



        const openViewModal = async (post: BoardListResponseDto) => {
            try {
                // Set the post data to viewablePost
                setViewablePost(post);
                // Open the ViewPostModal
                setViewModalOpen(true);
            } catch (error) {
                console.error('Error opening ViewPostModal:', error);
                // Handle error
            }
        };


        const handleDeleteClick = async (sid: number) => {
            const isConfirmed = window.confirm('Are you sure you want to delete?');
            if (isConfirmed) {
                try {
                    await deleteSquadApi(sid);
                    setViewablePost(null); // Set the viewablePost to null
                    refreshList(); // Refresh the list after successful deletion
                } catch (error) {
                }
            }
        };

        const refreshList = async () => {
            try {
                const squadsData = await fetchSquadsApi();
                const dataWithDefaultComments = squadsData.content.map((item: BoardListResponseDto) => ({
                    ...item,
                    commentList: item.commentList || [] // Ensures commentList is always an array
                }));
                setCurrentList(dataWithDefaultComments);
            } catch (error) {
                // Error handling is already done in fetchSquadsApi
                // Additional UI-based error handling can be added here if needed
            }
        };

        const openEditModal = (post: BoardListResponseDto) => {
            setEditablePost(post);
            setEditModalOpen(true);
        };

        const closeEditModal = () => {
            setEditModalOpen(false);
            setEditablePost(null);
        };

        const handleEditClick = (event: React.MouseEvent, post: BoardListResponseDto) => {
            event.stopPropagation(); // Prevent click event from reaching the parent div
            openEditModal(post);
        };

        useEffect(() => {
            const fetchSquads = async () => {
                try {
                    const dataWithDefaultComments = await fetchSquadsApi2();
                    setCurrentList(dataWithDefaultComments);
                } catch (error) {
                }
            };

            fetchSquads(); // Call immediately on component mount

            const intervalId = setInterval(() => {
                fetchSquads();
            }, 5000);

            return () => clearInterval(intervalId);
        }, []);

        return (
            <div>
                <div>
                    <Announce />

                    <div className="bg-[#232a34] text-white">
                        <div className="flex items-center justify-between p-4">
                            <div className="flex items-center space-x-2">
                                <button type="button" role="combobox" aria-controls="radix-:r2o:" aria-expanded="false" aria-autocomplete="none" dir="ltr" data-state="closed" data-placeholder="" className="flex h-10 w-full items-center justify-between rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50" id="league">
                                    <span>솔로랭크</span>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="h-4 w-4 opacity-50" aria-hidden="true">
                                        <path d="m6 9 6 6 6-6"></path>
                                    </svg>
                                </button>

                                <button className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 bg-[#4c586f]">티어 전체</button></div>

                            <div className="flex items-center space-x-2">
                                <button className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 text-black hover:bg-primary/90 h-10 px-4 py-2 bg-[#e8415d]" onClick={openModal}>
                                    클럽 DUO 찾기
                                </button>
                                {isModalOpen && <Modal onClose={closeModal}   onUpdate={refreshList}/>}
                            </div>
                        </div>
                        <div className="overflow-x-auto" id="board">
                            <table className="w-full text-sm table-fixed">
                                <thead>
                                <tr className="text-[#7a828e]">
                                    <th className="p-4 w-1/8">번호</th>
                                    <th className="p-4 w-1/8">소환사 이름</th>
                                    <th className="p-4 w-1/8">내 티어</th>
                                    <th className="p-4 w-1/8">내 라인</th>
                                    <th className="p-4 w-1/8">원하는 라인</th>
                                    <th className="p-4 w-1/8">마이크 여부</th>
                                    <th className="p-4 w-1/8">게임 타입</th>
                                    <th className="p-4 w-1/8">메모</th>
                                </tr>
                                </thead>
                                <tbody className="text-[#c6cdd4]">
                                {currentList.map((item, index) => (
                                    <tr key={index} onClick={() => openViewModal(item)}>
                                        <td className="p-4 w-1/8 ">{item.sid}</td>
                                        <td className="p-4 w-1/8">{item.summonerName}#{item.tagLine}</td>
                                        <td className="p-4 w-1/8">{item.rank}</td>
                                        <td className="p-4 w-1/8">{item.myPos}</td>
                                        <td className="p-4 w-1/8">{item.wantPos}</td>
                                        <td className="p-4 w-1/8">{item.useMic ? "Yes" : "No"}</td>
                                        <td className="p-4 w-1/8">{item.qtype}</td>
                                        <td className="p-4 w-1/8">{item.memo}</td>
                                        <td className="p-4 w-1/8">
                                            {userInfo?.lolNickname === item.summonerName && userInfo?.tag === item.tagLine && (
                                                <div className="flex space-x-2">
                                                    <button className="mr-3" onClick={(e) => handleEditClick(e, item)}>Edit</button>
                                                    <button onClick={() => handleDeleteClick(item.sid)}>Delete</button>
                                                </div>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                    {isEditModalOpen && editablePost && (
                        <EditPostModal post={editablePost} onClose={closeEditModal} onUpdate={refreshList} />
                    )}
                    {isViewModalOpen && viewablePost && (
                        <ViewPostModal post={viewablePost} onClose={() => setViewModalOpen(false)} />
                    )}
                    <div className="flex justify-center my-4">
                        <button
                            className="text-sm bg-[#3a4253] px-2 py-1 rounded"
                            onClick={loadMorePosts} // Update to call loadMorePosts function
                        >
                            Load More
                        </button>
                    </div>
                </div>


            </div>

        );
    }
