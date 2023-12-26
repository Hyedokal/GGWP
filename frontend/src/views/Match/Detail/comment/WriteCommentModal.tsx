import React, { useState } from 'react';
import Modal from 'react-modal';
import UserInfoStore from "../../../../stores/userInfo.store";
import './style.css';
import InputBox from "../../../../components/InputBox";
import {useCidInfoStore} from "../../../../stores";
import {postCommentApi} from "../../../../apis";
interface WriteCommentModalProps {
    sId: number;
    wontPos: string;
    qType: string;
    cId?: number;
    onCommentAdded?: () => void;
}
const WriteCommentModal: React.FC<WriteCommentModalProps> = ({ sId, wontPos, qType, onCommentAdded, cId }) => {
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [useMic, setUseMic] = useState(false);
    const [memo, setMemo] = useState('');
    const [memoError] = useState<boolean>(false);
    const [memoErrorMessage] = useState<string>('');
    const { setCidInfo } = useCidInfoStore();

    const userInfo = UserInfoStore(state => state.userInfo)
    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        clearForm();
    };
    const handleSubmit = async (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        try {
            const commentData = await postCommentApi(sId, wontPos, qType, useMic, userInfo ? userInfo.lolNickname : '', userInfo ? userInfo.tag : '', memo);

            setCidInfo({ cid: commentData });
            if (onCommentAdded) {
                onCommentAdded(); // Invoke the callback
            }
            closeModal();
        } catch (error) {
            console.error(error);
        }
    };


    const clearForm = () => {
        setUseMic(false);
        setMemo('');
    };

    return (
        <div >
            <button className="text-sm bg-[#3a4253] px-2 py-1 rounded" onClick={openModal}>댓글쓰기</button>
            <Modal  id="write-comment-modal"
                    ariaHideApp={false}  // Add this if you're not setting appElement
                    overlayClassName="ReactModal__Overlay"
                    className="ReactModal__Content"
                    isOpen={modalIsOpen} onRequestClose={closeModal}>

                <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">

                    <div className="bg-white rounded-lg p-8 w-full max-w-lg mx-auto">

                        <h2 className="text-xl font-semibold mb-4 text-black">댓글 쓰고 등록하기</h2>
                <form onSubmit={handleSubmit}>
                    <div className="grid grid-cols-2 gap-4 mb-4">
                        <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <div className="mr-3 text-black">소환사 이름:</div>
                            <div className="text-black">{userInfo?.lolNickname} </div>
                        </div>
                        <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <div className="mr-3 text-black">배틀 태그:</div>
                            <div className="text-black">{userInfo?.tag} </div>

                        </div>
                        <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <div className="mr-3 text-black">내포지션:</div>
                            <div className="text-black">{wontPos} </div>
                        </div>
                        <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                            <div className="mr-3 text-black">큐타입:</div>
                            <div className="text-black">{qType} </div>
                        </div>
                        <label>
                            Use Mic:
                            <input type="checkbox" checked={useMic} onChange={e => setUseMic(e.target.checked)} />
                        </label>
                    </div>
                    <div className="mb-4">
                        <InputBox
                            label="Memo"
                            type="text"
                            error={memoError}
                            errorMessage={memoErrorMessage}
                            placeholder="메모"
                            value={memo}
                            setValue={setMemo}
                        />
                    </div>
                    <button className=" text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2" type="submit">댓글작성</button>

                    <button className=" text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2"  type="button" onClick={closeModal}>닫기</button>
                </form>
                     </div>
                </div>

            </Modal>
        </div>
    );
};

export default WriteCommentModal;
