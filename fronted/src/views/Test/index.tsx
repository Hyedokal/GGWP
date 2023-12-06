import React, {useState} from 'react';
import axios from 'axios';

// Define the type for your request data
interface SquadRequestData {
    myPos: string;
    wantPos: string;
    qType: string;
    sMic: boolean;
    summonerName: string;
    sMemo: string;
}
// Define the base URL
const BASE_URL = 'http://localhost:8000';
// Define the endpoint path
const SQUAD_ENDPOINT = '/v1/squads';

// Define the base response structure
 interface ResponseDto1 {
    code: string;
    message: string;
}
 interface SquadResponseData extends ResponseDto1 {
}

// Define the response structure for Squad requests, extending ResponseDto

export default function Test() {
    // State for each field in the form
    const [myPos, setMyPos] = useState('');
    const [wantPos, setWantPos] = useState('');
    const [qType, setQType] = useState('');
    const [sMic, setSMic] = useState(false);
    const [summonerName, setSummonerName] = useState('');
    const [sMemo, setSMemo] = useState('');


    const sendPostRequest = async () => {
        const requestData: SquadRequestData = {
            myPos,
            wantPos,
            qType,
            sMic,
            summonerName,
            sMemo,

        };

        try {
            const response = await axios.post<SquadResponseData>(BASE_URL + SQUAD_ENDPOINT, requestData);
            console.log(response.data);
            // Handle response here
        } catch (error) {
            console.error('Error sending POST request:', error);
            // Handle error here
        }
    };

    return(
        <div>
            <div>
                <input type="text" value={myPos} onChange={(e) => setMyPos(e.target.value)} placeholder="My Position"/>
                <input type="text" value={wantPos} onChange={(e) => setWantPos(e.target.value)} placeholder="Wanted Position"/>
                <input type="text" value={qType} onChange={(e) => setQType(e.target.value)} placeholder="Queue Type"/>
                <label>
                    <input type="checkbox" checked={sMic} onChange={(e) => setSMic(e.target.checked)}/>
                    Use Mic
                </label>
                <input type="text" value={summonerName} onChange={(e) => setSummonerName(e.target.value)} placeholder="Summoner Name"/>
                <input type="text" value={sMemo} onChange={(e) => setSMemo(e.target.value)} placeholder="Short Memo"/>
                <button onClick={sendPostRequest}>Send POST Request</button>
            </div>
            <div className="min-h-screen bg-gray-200 flex justify-center items-center">
                <div className="bg-white rounded-lg p-8 w-full max-w-lg mx-auto">
                    <h2 className="text-xl font-semibold mb-4">글쓰고 등록하기</h2>
                    <div className="grid grid-cols-2 gap-4 mb-4">
                        <input
                            className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            placeholder="kim@gmail.com"
                        />
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

                        <input
                            className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            type="text"
                            value={summonerName}
                            onChange={(e) => setSummonerName(e.target.value)}
                            placeholder="소환사 이름"
                        />
                    </div>
                    <div className="mb-4">
                        <input
                            className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            type="text"
                            value={sMemo}
                            onChange={(e) => setSMemo(e.target.value)}
                            placeholder="메모"
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 mr-2">
                            취소
                        </button>
                        <button
                            onClick={sendPostRequest}
                            className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2">
                            등록
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}