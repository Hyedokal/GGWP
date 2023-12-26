import React, {ChangeEvent, useState} from 'react';
import InputBox from "../InputBox";
import SquadRequestDto from "../../apis/dto/request/squad/SquadRequestDto";
import {sendSquadRequest} from "../../apis";
import UserInfoStore from "../../stores/userInfo.store";


 interface ModalProps {
        onClose: () => void;
        onUpdate: () => void; // Callback to refresh the list after update

 }


const Modal : React.FC<ModalProps>  = ({ onClose,onUpdate  }) => {
        const [myPos, setMyPos] = useState('');
        const [wantPos, setWantPos] = useState('');
        const [qType, setQType] = useState('');
        const [useMic, setUseMic] = useState(false);
        const [memo, setMemo] = useState('');
        const [sMemoError, setSMemoError] = useState<boolean>(false);
        const [sMemoErrorMessage, setSMemoErrorMessage] = useState<string>('');
        const {userInfo} = UserInfoStore();



        const sendPostRequest = async () => {
            let hasError = false;

            if (memo.includes("fuck")) {
                setSMemoError(true);
                setSMemoErrorMessage("비속어를 쓰면 안된다고");
                hasError = true;
            }

            if (myPos === wantPos) {
                alert("포지션이 같으면 안됩니다");
                hasError = true;
            }
            if (hasError) return;

            const requestBody: SquadRequestDto = {
                myPos,
                wantPos,
                qType,
                useMic,
                summonerName: userInfo?.lolNickname || '',// || ' ' 를 사용해서 소환사 이름을 안전하게 할당
                tagLine:userInfo?.tag||'',
                memo,
            };
            try {
                const response = await sendSquadRequest(requestBody);
                console.log(response); // Handle the response as needed
                onUpdate(); // Refresh the list

                onClose();


            } catch (error) {
                console.error('Error sending POST request:', error);
                // Handle error here
            }

        };

        return (

            <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center font-black">

                    <div className="bg-white rounded-lg p-8 w-full max-w-lg mx-auto">
                        <h2 className="text-xl font-semibold mb-4 text-black">글쓰고 등록하기</h2>
                        <div className="grid grid-cols-2 gap-4 mb-4">
                            <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                                <div className="mr-3 text-black">소환사 이름:</div>
                                    <div className="text-black">{userInfo?.lolNickname} </div>
                            </div>
                            <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                                <div className="mr-3 text-black">배틀 태그:</div>
                                <div className="text-black">{userInfo?.tag} </div>

                            </div>
                            <select
                                className=" text-black flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                value={myPos}
                                onChange={(e) => setMyPos(e.target.value)}
                            >
                                <option value="">내가 할 포지션</option>
                                <option value="TOP">TOP</option>
                                <option value="JUNGLE">JUNGLE</option>
                                <option value="MID">MID</option>
                                <option value="ADC">ADC</option>
                                <option value="SUPPORT">SUPPORT</option>
                                <option value="FLEX">FLEX</option>
                            </select>
                            <select
                                className=" text-black flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                value={wantPos}
                                onChange={(e) => setWantPos(e.target.value)}
                            >
                                <option value="">구하고싶은 포지션</option>
                                <option value="TOP">TOP</option>
                                <option value="JUNGLE">JUNGLE</option>
                                <option value="MID">MID</option>
                                <option value="ADC">ADC</option>
                                <option value="SUPPORT">SUPPORT</option>
                                <option value="FLEX">FLEX</option>
                            </select>
                            <select
                                className=" text-black flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                value={qType}
                                onChange={(e) => setQType(e.target.value)}
                            >
                                <option value="">큐 타입</option>
                                <option value="SOLO_RANK">솔로 랭크</option>
                                <option value="FLEX_RANK">자유 랭크</option>
                                <option value="HOWLING_ABYSS">칼바람 나락</option>
                            </select>
                            <label className=" text-black flex items-center space-x-2">
                                <input
                                    className="rounded border border-input bg-background text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
                                    type="checkbox"
                                    checked={useMic}
                                    onChange={(e) => setUseMic(e.target.checked)}
                                />
                                <span className="text-sm text-muted-foreground">
                            마이크 여부
                        </span>
                            </label>

                        </div>
                        <div className="mb-4">
                            <InputBox
                                label="Memo"
                                type="text"
                                error={sMemoError}
                                errorMessage={sMemoErrorMessage}
                                placeholder="메모"
                                value={memo}
                                setValue={setMemo}
                            />
                        </div>
                        <div className="flex justify-end">
                            <button   className=" text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2" onClick={onClose}>
                                취소
                            </button>

                            <button
                                onClick={sendPostRequest}
                                className=" text-black inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2">
                                등록
                            </button>
                        </div>
-                </div>
                </div>

        );
    };

export default Modal;



