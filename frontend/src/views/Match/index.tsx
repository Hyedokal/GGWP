    import React, { useEffect, useState } from "react";
    import axios from 'axios';
    import UserInfoStore from "../../stores/userInfo.store";
    import {useNavigate} from "react-router-dom";
    import Modal from "../../components/ModalWrite";
    import {BoardListResponseDto} from "./BoardListResponseDto";
    import EditPostModal from "./Edit/EditPostModal";
    import {findAllByDisplayValue} from "@testing-library/react";
    import ViewPostModal from "./Detail/ViewPostModal";
    import './style.css';

    export default function Match() {
        const { userInfo } = UserInfoStore();

        const [isModalOpen, setModalOpen] = useState(false);
        const [currentList, setCurrentList] = useState<BoardListResponseDto[]>([]);
        const [currentPage, setCurrentPage] = useState(0);
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
                const response = await axios.post('http://localhost:8000/v1/squads/search', {
                    outdated: false,
                    page: currentPage,
                    size: newSize
                });
                const dataWithDefaultComments = response.data.content.map((item: BoardListResponseDto) => ({
                    ...item,
                    commentList: item.commentList || []
                }));
                setCurrentList((prevList) => {
                    // Combine existing list with new data, avoiding duplicates
                    const existingIds = new Set(prevList.map(item => item.sid));
                    const newData = dataWithDefaultComments.filter((item: { sid: number; }) => !existingIds.has(item.sid));
                    return [...prevList, ...newData];
                });
                setLoadMoreSize(newSize); // Update loadMoreSize state
            } catch (error) {
                console.error('Error fetching additional squads data:', error);
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
            // 사용자에게 삭제를 확인하는 메시지를 표시
            const isConfirmed = window.confirm('Are you sure you want to delete?');

            // 사용자가 '확인'을 클릭했을 때만 삭제 진행
            if (isConfirmed) {
                try {
                    await axios.delete(`http://localhost:8000/v1/squads/${sid}`);
                    setViewablePost(null); // Get the viewablePost null background
                    refreshList(); // 성공적으로 삭제된 후 목록을 새로고침
                } catch (error) {
                    console.error('Error deleting squad:', error);
                    // 오류 처리
                }
            }
        };

        const refreshList = async () => {
            try {
                const response = await axios.post('http://localhost:8000/v1/squads/search', {
                    outdated: false,
                    page: 0,
                    size: 15
                });
                const dataWithDefaultComments = response.data.content.map((item: BoardListResponseDto) => ({
                    ...item,
                    commentList: item.commentList || [] // Ensures commentList is always an array
                }));
                setCurrentList(dataWithDefaultComments);
            } catch (error) {
                console.error('Error fetching squads data:', error);
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
                    const response = await axios.post('http://localhost:8000/v1/squads/search', {
                        outdated: false,
                        page: 0,
                        size: 15
                    });
                    const dataWithDefaultComments = response.data.content.map((item: BoardListResponseDto) => ({
                        ...item,
                        commentList: item.commentList || [] // Ensures commentList is always an array
                    }));
                    setCurrentList(dataWithDefaultComments);
                } catch (error) {
                    console.error('Error fetching squads data:', error);
                    // Handle the error appropriately
                }
            };
            fetchSquads(); // Call immediately on component mount

            const intervalId = setInterval(() => {
                fetchSquads();
            }, 5000);

            return () => clearInterval(intervalId); //
        }, []); // Empty dependency array ensures effect is only run on mount and unmount

        return (
            <div>
                <div>
                    <div className="bg-[#232a34] text-white">
                        <div className="flex items-center justify-between p-4">
                            <div className="flex items-center space-x-2"><button type="button" role="combobox" aria-controls="radix-:r2o:" aria-expanded="false" aria-autocomplete="none" dir="ltr" data-state="closed" data-placeholder="" className="flex h-10 w-full items-center justify-between rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50" id="league"><span>솔로랭크</span><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="h-4 w-4 opacity-50" aria-hidden="true">
                                <path d="m6 9 6 6 6-6"></path>
                            </svg></button><button className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 bg-[#4c586f]">티어 전체</button></div>

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
                                    <th className="p-4 w-1/8">내 라인</th>
                                    <th className="p-4 w-1/8">내 티어</th>
                                    <th className="p-4 w-1/8">원하는 라인</th>
                                    <th className="p-4 w-1/8">마이크 여부</th>
                                    <th className="p-4 w-1/8">게임 타입</th>
                                    <th className="p-4 w-1/8">메모</th>
                                </tr>
                                </thead>
                                <tbody className="text-[#c6cdd4]">
                                {currentList.map((item, index) => (
                                    <tr key={index} onClick={() => openViewModal(item)}> {/* Add onClick handler */}
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
