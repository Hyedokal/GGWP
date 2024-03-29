import {useCookies} from "react-cookie";
import UserInfoStore from "../../stores/userInfo.store";
import {useEffect, useState} from "react";
import ViewPersonalities from "./personalities/ViewPersonalities";
import {useLocation} from "react-router-dom";
import {UserInfo} from "../../types";
import ProfileImg from "./profile/profile";
import './style.css';
import {fetchMatchesApi, fetchMatchHistoryApi, fetchOpponentsInfoApi, updateUserInfoApi} from "../../apis";
import RequestSender from "./personalities/personalitiesSend";

export interface MatchHistoryResponse {

    code: string;
    message: string;
    lolNickname: string;
    tag: string;
    sid: number[];
}
export interface CommentMatchResponse {
    sids: number;
    squadNickname: string;
    squadTag: string;
    myName: string;
    myTag: string;
}

interface Opponent {
    id: number;
    summonerName: string;
    tagLine: string;
}
export default function User() {


        const [cookies] = useCookies();
        const { userInfo, setUserInfo } = UserInfoStore();
        const [opponents, setOpponents] = useState<Opponent[]>([]);
        const [matchHistory, setMatchHistory] = useState<MatchHistoryResponse[]>([]); // This holds an array of responses
            const location = useLocation();
        const [newLolNickName, setNewLolNickName] = useState(userInfo?.lolNickname || '');
        const [newTag, setNewTag] = useState(userInfo?.tag || '');
    const [editMode, setEditMode] = useState(false);

    const [matches, setMatches] = useState<CommentMatchResponse[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);


    const fetchMatches = async () => {
        setIsLoading(true);
        try {
            const matchesData = await fetchMatchesApi(userInfo?.lolNickname, userInfo?.tag);
            setMatches(matchesData);
        } catch (error) {
            // Error handling is already done in fetchMatchesApi
            // Additional UI-based error handling can be added here if needed
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        // Optionally, call fetchMatches here if you want it to run on component mount
        fetchMatches();
    }, []); // Empty dependency array means this effect runs once on mount

    const handleUpdate = async () => {
        try {
            const response = await updateUserInfoApi(newLolNickName, newTag, cookies.accessToken);


            if(response.data.code === "SU") {
                setUserInfo({
                    ...userInfo,
                    lolNickname: newLolNickName,
                    tag: newTag,
                    email: userInfo?.email ?? "",
                    userRole: userInfo?.role ?? undefined
                } as UserInfo);
                await fetchMatchHistory();
                console.log('Update successful and match history re-fetched');

            }
        } catch (error) {
                console.error('Error in updating:', error);
                alert('중복닉 or 태그 입니다.');

        }
    };

    const fetchOpponentsInfo = async (ids: number[]) => {
        try {
            const response = await fetchOpponentsInfoApi(ids);
            setOpponents(response.data); // Assuming the response data is an array of Opponent objects
        } catch (error) {
            console.error('Error fetching opponents info:', error);
        }
    };
    useEffect(() => {
        if (matchHistory.length > 0) {
            const ids = matchHistory.flatMap(history => history.sid);
            fetchOpponentsInfo(ids);
        }
    }, [matchHistory]); // Dependency on matchHistory to re-fetch when it changes


    const fetchMatchHistory = async (): Promise<void> => {
        try {
            const response = await fetchMatchHistoryApi(cookies.accessToken);

            if (response.data.code === "SU") {
                setMatchHistory([response.data]); // Corrected the syntax here

            } else {
                // Handle other response codes
                console.error('Failed to fetch match history:', response.data.message);
            }
        } catch (error) {
            console.error('Error fetching match history:', error);
        }
    };

    // useEffect to fetch match history on component mount
    useEffect(() => {
        if (location.pathname === '/user') {
            fetchMatchHistory();
            fetchMatches();
        }
    }, [location]);



    return(
        <div>

            <div className="flex h-screen bg-gray-100 dark:bg-gray-800">
                <div className="w-1/4 p-8">
                    <div className="flex flex-col items-center p-4 bg-white dark:bg-gray-700 rounded-lg shadow">
                        <ProfileImg/>
                    </div>
                    <div className="mt-8 bg-teal-300 dark:bg-teal-700 rounded-lg p-4">
                        <div className="mt-2 text-lg font-bold text-gray-900 dark:text-gray-100 mb-2">이메일:</div>

                        <div className="flex justify-between items-center">
                            <div className="text-lg font-semibold text-gray-900 dark:text-gray-100">롤닉네임</div>
                            <h1 className="text-lg font-semibold text-gray-900 dark:text-gray-100">{userInfo?.lolNickname}</h1>
                        </div>

                        <div className="flex justify-between items-center">
                            <div className="text-lg font-semibold text-gray-900 dark:text-gray-100">태그</div>
                            <h1 className="text-lg font-semibold text-gray-900 dark:text-gray-100">{userInfo?.tag}</h1>
                        </div>

                        {/* Toggle Edit Mode Button */}
                        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={() => setEditMode(!editMode)}>Edit</button>

                        {/* Conditional Rendering of Input Fields */}
                        {editMode && (
                            <div className="flex flex-col mt-4">
                                <input
                                    className="border border-gray-300 rounded-md px-4 py-2 mb-2"
                                    type="text"
                                    value={newLolNickName}
                                    onChange={(e) => setNewLolNickName(e.target.value)}
                                    placeholder="New lolNickname"
                                />
                                <input
                                    className="border border-gray-300 rounded-md px-4 py-2 mb-2"
                                    type="text"
                                    value={newTag}
                                    onChange={(e) => setNewTag(e.target.value)}
                                    placeholder="New Tag"
                                />
                                <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" onClick={handleUpdate}>Update</button>
                            </div>
                        )}

                        {/* ...rest of your component... */}
                    </div>
                    <div className="mt-8 bg-teal-300 dark:bg-teal-700 rounded-lg p-4">
                        <div className="text-lg font-semibold text-gray-900 dark:text-gray-100">성격:</div>
                        <ViewPersonalities/>
                    </div>
                    <RequestSender/>

                </div>

                <div className="w-3/4 p-8">
                    <div className="grid grid-cols-1 gap-4 font-bold text-lg text-purple-600">

                    <div className="h-12  dark:bg-teal-200 rounded-lg">
                        <h1>MY DUO 내역</h1>
                    </div>
                    </div>
                    <div className={`grid grid-cols-1 gap-4 font-bold text-lg text-purple-600 ${matches.length > 20 ? 'scrollable-container' : ''}`}>
                        {/* Check if matchHistory is empty and display a message */}
                        {matchHistory.length === 0 ? (
                            <div>Loading match history...</div>
                        ) : (
                            matchHistory.map((historyItem, index) => (
                                <div key={index} className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg">
                                    {historyItem.sid.map((match, matchIndex) => {
                                        // Find the corresponding opponent
                                        const opponent = opponents.find(op => op.id === match);
                                        return (
                                            <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg mb-10"  key={match}>

                                                매치 번호 : {match}
                                                <span style={{ marginLeft: '60px' }}> {/* Add margin here */}
                                                    글작성자 :{historyItem.lolNickname}#{historyItem.tag}
                                                </span>

                                                {opponent && (
                                                    <span style={{ marginLeft: '60px' }}>
                                                  매칭자:  {opponent.summonerName}#{opponent.tagLine}
                                                   </span>
                                                )}
                                            </div>
                                        );
                                    })}
                                </div>
                            ))
                        )}
                    </div>
                </div>

                <div className="w-3/4 p-8">
                    <div className="grid grid-cols-1 gap-4 font-bold text-lg text-purple-600">
                        <div className="h-12 dark:bg-teal-200 rounded-lg">
                            <h1>신청 DUO 내역</h1>
                        </div>
                    </div>
                    <div className={`grid grid-cols-1 gap-4 font-bold text-lg text-purple-600 ${matches.length > 20 ? 'scrollable-container' : ''}`}>
                        {/* Check if matches array is empty and display a message */}
                        {isLoading ? (
                            <div>Loading...</div>
                        ) : (
                            matches.length === 0 ? (
                                <div>No match history available.</div>
                            ) : (
                                matches.map((match, index) => (
                                    <div key={index} className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg">
                                        매치 번호 : {match.sids}
                                        <span style={{ marginLeft: '60px' }}> {/* Add margin here */}
                                            댓글 작성자 :{match.myName}#{match.myTag}
                        </span>
                                        <span style={{ marginLeft: '60px' }}>
                            매칭자:  {match.squadNickname}#{match.squadTag}
                        </span>
                                    </div>
                                ))
                            )
                        )}
                    </div>
                </div>

                    </div>
                </div>



    )

    }
