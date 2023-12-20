import UserInfoStore from "../../stores/userInfo.store";
import React, {ChangeEvent, useEffect, useState} from "react";
import axios from "axios";
import './style.css';
import {render} from "react-dom";

interface Announce {
    id: number;
    title: string;
    content: string;
    updatedAt: string;
}
interface InputBoxProps {
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const InputBox: React.FC<InputBoxProps> = ({ value, onChange }) => {
    return<input className="input-announce" type="text" value={value} onChange={onChange} />;

};
interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    offset: number;
    unpaged: boolean;
    paged: boolean;
}

interface AnnounceResponse {
    content: Announce[];
    pageable: Pageable;
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}

const Announce: React.FC = () => {
    const { userInfo } = UserInfoStore();
    const [announces, setAnnounces] = useState<AnnounceResponse | null>(null);
    const [currentPage, setCurrentPage] = useState(0);


    const [editingAnnounceId, setEditingAnnounceId] = useState<number | null>(null);
    const [editTitle, setEditTitle] = useState('');
    const [editContent, setEditContent] = useState('');


    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newTitle, setNewTitle] = useState('');
    const [newContent, setNewContent] = useState('');
    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);




    
    const handleNewPost = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        axios.post('http://localhost:8000/v1/announces', {
            title: newTitle,
            content: newContent
        })
            .then(response => {
                fetchAnnouncements(currentPage); // Re-fetch announcements
                closeModal();
            })
            .catch(error => {
                console.error('Error creating new announcement', error);
            });
    };

    const deleteAnnouncement = (announceId: number) => {
        axios.delete(`http://localhost:8000/v1/announces/${announceId}`)
            .then(() => {
                // Remove the deleted announcement from the local state
                if (announces) {
                    const updatedAnnounces = announces.content.filter(announce => announce.id !== announceId);
                    setAnnounces({ ...announces, content: updatedAnnounces });
                }
            })
            .catch(error => {
                console.error('Error deleting announcement', error);
            });
    };


    const startEdit = (announce: Announce) => {
        setEditingAnnounceId(announce.id);
        setEditTitle(announce.title);
        setEditContent(announce.content);
    };
    const cancelEdit = () => {
        setEditingAnnounceId(null);
    };
    const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setEditTitle(event.target.value);
    };

    const handleContentChange = (event: ChangeEvent<HTMLInputElement>) => {
        setEditContent(event.target.value);
    };
    const saveEdit = () => {
        axios.put(`http://localhost:8000/v1/announces/${editingAnnounceId}`, {
            title: editTitle,
            content: editContent
        })
            .then(response => {
                fetchAnnouncements(currentPage); // Re-fetch announcements
                setEditingAnnounceId(null);
            })
            .catch(error => {
                console.error('Error updating announcement', error);
            });
    };


    useEffect(() => {
        fetchAnnouncements(currentPage);
    }, [currentPage]);

    const fetchAnnouncements = (page: number) => {
        axios.post('http://localhost:8000/v1/announces/search', { page: page })
            .then(response => {
                setAnnounces(response.data);
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const renderPageNumbers = () => {
        const totalPages = announces?.totalPages ?? 0;
        const pages = [];
        for (let i = 0; i < totalPages; i++) {
            pages.push(
                <button
                    className={`text-xs px-2 py-1 border border-gray-300 rounded ${i === currentPage ? 'bg-blue-500 text-white' : 'bg-white hover:bg-gray-100'}`}
                    key={i}
                    onClick={() => handlePageChange(i)}
                    disabled={i === currentPage}
                >
                    {i}
                </button>
            );
        }
        return pages;
    };

    return (

        <div id="main">
            <section className="notice">
                <div className="page-title">
                    <div className="container">
                        <h3>공지사항</h3>
                    </div>
                </div>

                <div id="board-search">
                    <div className="container">
                    </div>
                </div>

                <div id="board-list">
                    {announces && (
                        <div className="container">
                            <table className="board-table">
                                <thead>
                                <tr>
                                    <th scope="col" className="th-num">번호</th>
                                    <th scope="col" className="th-title">제목</th>
                                    <th scope="col" className="th-content">내용</th>
                                    <th scope="col" className="th-date">등록일</th>
                                </tr>
                                </thead>
                                <tbody>
                                {announces.content.map((announce, index) => {
                                    const dateObject = new Date(announce.updatedAt);
                                    const formattedDate = `${dateObject.getFullYear()}-${(dateObject.getMonth() + 1).toString().padStart(2, '0')}-${dateObject.getDate().toString().padStart(2, '0')}`;

                                    return (
                                        editingAnnounceId === announce.id ? (
                                            // Render editable fields for the selected announcement
                                            <tr key={index}>
                                                <td>{announce.id}</td>
                                                <td>
                                                    <div className="group">
                                                    <InputBox   value={editTitle} onChange={handleTitleChange} />
                                                        <span className="highlight"></span>
                                                        <span className="bar"></span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div className="group">
                                                    <InputBox value={editContent} onChange={handleContentChange} />
                                                        <span className="highlight"></span>
                                                        <span className="bar"></span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div className="flex justify-center space-x-4">
                                                        <button className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded-full" onClick={saveEdit}>
                                                            Save
                                                        </button>
                                                        <button className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded-full" onClick={cancelEdit}>
                                                            Cancel
                                                        </button>
                                                    </div>                                                </td>
                                            </tr>
                                        ) : (
                                            // Render announcement data
                                            <tr  key={index}>
                                                <td>{announce.id}</td>
                                                <td>[공지사항]{announce.title}</td>
                                                <td>{announce.content}</td>
                                                <td>{formattedDate}</td>
                                                {userInfo?.role === 'ADMIN' && (
                                                    <td>
                                                        <div className="flex justify-center space-x-4">
                                                            <button className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded-full"
                                                                    onClick={() => startEdit(announce)}>Edit
                                                            </button>
                                                            <button className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded-full"
                                                                    onClick={() => deleteAnnouncement(announce.id)}>Delete
                                                            </button>
                                                        </div>
                                                    </td>
                                                )}
                                            </tr>
                                        )
                                    );
                                })}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
                {userInfo?.role === 'ADMIN' && (
                    <div className="flex justify-center space-x-4">
                        <button className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-1 px-2 rounded-full" onClick={openModal}>Write</button>
                    </div>
                        )}


                {/* Modal for creating a new post */}
                {isModalOpen && (
                    <div className="modal bg-white shadow-lg rounded-lg p-6">
                        <form onSubmit={handleNewPost} className="space-y-4">
                            <input
                                type="text"
                                value={newTitle}
                                onChange={(e) => setNewTitle(e.target.value)}
                                placeholder="Title"
                                className="w-full p-2 border border-gray-300 rounded"
                            />
                            <textarea
                                value={newContent}
                                onChange={(e) => setNewContent(e.target.value)}
                                placeholder="Content"
                                className="w-full p-2 border border-gray-300 rounded h-32"
                            />

                            <div className="flex justify-end space-x-2">
                                <button type="submit" className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded">
                                    Create Post
                                </button>
                                <button onClick={closeModal} className="bg-gray-500 hover:bg-gray-400 text-white font-bold py-2 px-4 rounded">
                                    Cancel
                                </button>
                            </div>
                        </form>
                    </div>
                )}
                <div className="flex justify-center space-x-2">
                    <button
                        className={`px-3 py-1.5 text-sm font-medium rounded-md ${currentPage === 0 ? 'bg-gray-300 text-gray-500 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-600 text-white'}`}
                        onClick={() => handlePageChange(Math.max(currentPage - 1, 0))}
                        disabled={currentPage === 0}>
                        전
                    </button>
                    {renderPageNumbers()}
                    <button
                        className={`px-3 py-1.5 text-sm font-medium rounded-md ${announces?.last ? 'bg-gray-300 text-gray-500 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-600 text-white'}`}
                        onClick={() => handlePageChange(Math.min(currentPage + 1, (announces?.totalPages ?? 1) - 1))}
                        disabled={announces?.last ?? true}>
                        후
                    </button>                </div>
            </section>
        </div>
    )
}

export default Announce;
