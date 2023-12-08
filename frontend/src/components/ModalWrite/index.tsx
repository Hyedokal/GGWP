import React, {ChangeEvent, useState} from 'react';
import InputBox from "../InputBox";
import SquadRequestDto from "../../apis/dto/request/squad/SquadRequestDto";
import {sendSquadRequest} from "../../apis";
import UserInfoStore from "../../stores/userInfo.store";


 interface ModalProps {
        onClose: () => void;
    }


const Modal : React.FC<ModalProps>  = ({ onClose  }) => {
        const [myPos, setMyPos] = useState('');
        const [wantPos, setWantPos] = useState('');
        const [qType, setQType] = useState('');
        const [sMic, setSMic] = useState(false);
        const [summonerName, setSummonerName] = useState('');
        const [sMemo, setSMemo] = useState('');
        const [sMemoError, setSMemoError] = useState<boolean>(false);
        const [sMemoErrorMessage, setSMemoErrorMessage] = useState<string>('');
        const {userInfo} = UserInfoStore();




        const sendPostRequest = async () => {
            let hasError = false;

            if (sMemo.includes("fuck")) {
                setSMemoError(true);
                setSMemoErrorMessage("비속어를 쓰면 안된다고");
                hasError = true;
            }
            if (hasError) return;


            const requestBody: SquadRequestDto = {
                myPos,
                wantPos,
                qType,
                sMic,
                summonerName: userInfo?.lolNickname || '',// || ' ' 를 사용해서 소환사 이름을 안전하게 할당
                sMemo,
            };
            try {
                const response = await sendSquadRequest(requestBody);
                console.log(response); // Handle the response as needed
                onClose();

            } catch (error) {
                console.error('Error sending POST request:', error);
                // Handle error here
            }

        };

        return (

            <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">

                    <div className="bg-white rounded-lg p-8 w-full max-w-lg mx-auto">
                        <h2 className="text-xl font-semibold mb-4">글쓰고 등록하기</h2>
                        <div className="grid grid-cols-2 gap-4 mb-4">
                            <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                                <div className="mr-3">소환사 이름:</div>
                                {userInfo?.lolNickname}
                            </div>
                            <div className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                                <div className="mr-3">배틀 태그:</div>
                                {userInfo?.tag}
                            </div>
                            <select
                                className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
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
                                className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
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
                                className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                value={qType}
                                onChange={(e) => setQType(e.target.value)}
                            >
                                <option value="">큐 타입</option>
                                <option value="SOLO_RANK">솔로 랭크</option>
                                <option value="FLEX_RANK">자유 랭크</option>
                                <option value="HOWLING_ABYSS">칼바람 나락</option>
                            </select>
                            <label className="flex items-center space-x-2">
                                <input
                                    className="rounded border border-input bg-background text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
                                    type="checkbox"
                                    checked={sMic}
                                    onChange={(e) => setSMic(e.target.checked)}
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
                                value={sMemo}
                                setValue={setSMemo}
                            />
                        </div>
                        <div className="flex justify-end">
                            <button   className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2" onClick={onClose}>
                                취소
                            </button>

                            <button
                                onClick={sendPostRequest}
                                className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2">
                                등록
                            </button>
                        </div>
-                </div>
                </div>

        );
    };

export default Modal;



