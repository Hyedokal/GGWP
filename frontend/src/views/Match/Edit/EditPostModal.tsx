// EditPostModal.tsx
import React, {useState} from 'react';
import {BoardListResponseDto} from "../BoardListResponseDto";
import axios from "axios";
import "./style.css"
import {updateSquadApi} from "../../../apis";
interface EditPostModalProps {
    post: BoardListResponseDto;
    onClose: () => void;
    onUpdate: () => void; // Callback to refresh the list after update

}

const EditPostModal: React.FC<EditPostModalProps> = ({ post, onClose, onUpdate }) => {
    const [editedPost, setEditedPost] = useState<BoardListResponseDto>({ ...post });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const target = e.target as HTMLInputElement;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        setEditedPost({ ...editedPost, [target.name]: value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await updateSquadApi(post.sid, editedPost);
            onUpdate(); // Refresh the list
            onClose(); // Close the modal
        } catch (error) {
        }
    };



    return (
        <div  className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">

            <div className="bg-white rounded-lg p-8 w-full max-w-lg mx-auto">
                <h2 className="text-xl font-semibold mb-4 text-black">Edit Post</h2>
                <div className="grid grid-cols-2 gap-4 mb-4">
                    <form onSubmit={handleSubmit}>
                        <div style={{ width: '450px', margin: 'auto' }} className=" flex h-10 w-screen rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <label className="mr-40 text-black">메모:</label>
                            <input
                                className="text-black"
                                type="text"
                                name="memo"
                                value={editedPost.memo}
                                onChange={handleChange}

                            />
                        </div>
                        {/* My Position Field */}
                        <div style={{ width: '450px', margin: 'auto' }} className=" flex h-10 w-screen rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <label className="mr-40 text-black">내 포지션:</label>
                            <select
                                className="text-black"
                                name="myPos"
                                value={editedPost.myPos}
                                onChange={handleChange}
                            >
                                <option value="TOP">TOP</option>
                                <option value="JUNGLE">JUNGLE</option>
                                <option value="MID">MID</option>
                                <option value="ADC">ADC</option>
                                <option value="SUPPORT">SUPPORT</option>
                                <option value="FLEX">FLEX</option>
                            </select>
                        </div>
                        {/* Wanted Position Field */}
                        <div style={{ width: '450px', margin: 'auto' }} className=" flex h-10 w-screen rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <label className="mr-40 text-black">원하는 포지션:</label>
                            <select
                                className="text-black"
                                name="wantPos"
                                value={editedPost.wantPos}
                                onChange={handleChange}
                            >
                                <option value="TOP">TOP</option>
                                <option value="JUNGLE">JUNGLE</option>
                                <option value="MID">MID</option>
                                <option value="ADC">ADC</option>
                                <option value="SUPPORT">SUPPORT</option>
                                <option value="FLEX">FLEX</option>                            </select>
                        </div>
                        {/* Queue Type Field */}
                        <div style={{ width: '450px', margin: 'auto' }} className=" flex h-10 w-screen rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <label className="mr-40 text-black">큐타입:</label>
                            <select
                                className="text-black"
                                name="qtype"
                                value={editedPost.qtype}
                                onChange={handleChange}
                            >
                                <option value="SOLO_RANK">SOLO_RANK</option>
                                <option value="FLEX_RANK">FLEX_RANK</option>
                                <option value="HOWLING_ABYSS">HOWLING_ABYSS</option>
                            </select>
                        </div>
                        {/* Mic Usage Field */}
                        <div style={{ width: '450px', margin: 'auto' }} className=" flex h-10 w-screen rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <label className="text-black">
                                마이크 여부:
                                <input
                                    className="group-checked:text-accent-foreground ml-60"
                                    type="checkbox"
                                    name="useMic"
                                    checked={editedPost.useMic}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        {/* Buttons */}
                        <div className="flex justify-self-stretch">
                            <button
                                className="text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2"
                                onClick={onClose}>
                                취소
                            </button>
                            <button
                                type="submit"
                                className="text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2">
                                등록
                            </button>
                        </div>
                    </form>
            </div>
        </div>
        </div>
    );
};

export default EditPostModal;;
