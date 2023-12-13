import {useCookies} from "react-cookie";
import UserInfoStore from "../../stores/userInfo.store";
import {useState} from "react";
import {patchLolNicknameRequest} from "../../apis";
import RequestSender from "./personalities/personalitiesSend";
import ViewPersonalities from "./personalities/ViewPersonalities";


export default function User() {


        const [cookies] = useCookies();
        const { userInfo, setUserInfo } = UserInfoStore();
        const [editMode, setEditMode] = useState(false);
        const [newLolNickname, setNewLolNickname] = useState(userInfo?.lolNickname || '');


    //닉네임을 업데이트하는 handler 함수
    const updateLolNickname = async () => {
        if (!newLolNickname) {
            alert('Please enter a nickname');
            return;
        }

        const result = await patchLolNicknameRequest({ lolNickName: newLolNickname }, cookies.accessToken);
        if (result === 'SU') { // Assuming 'SU' is the success code
            if (userInfo) {
                console.log(`닉네임 바뀌기 전후  ${userInfo.lolNickname} to ${newLolNickname}`);

                setUserInfo({
                    ...userInfo,
                    lolNickname: newLolNickname,
                    email: userInfo.email, // Ensuring all fields are present
                    tag: userInfo.tag,
                    userRole: userInfo.userRole
                });
            } else {
                // Handle the case where userInfo is null or doesn't have all fields
            }
            setEditMode(false);
        } else {
            // Handle other codes or error scenarios
            alert('Failed to update nickname');
        }
    };

    return(
        <div>

            <div className="flex h-screen bg-gray-100 dark:bg-gray-800">
                <div className="w-1/4 p-8">
                    <div className="flex flex-col items-center p-4 bg-white dark:bg-gray-700 rounded-lg shadow">
                        <div className="w-24 h-24 bg-gray-300 dark:bg-gray-500 rounded-full"></div>
                        <div className="mt-4 text-lg font-semibold text-gray-900 dark:text-gray-100">프로필</div>
                    </div>
                    <div className="mt-8 bg-teal-300 dark:bg-teal-700 rounded-lg p-4">
                        <div className="mt-2 text-lg font-bold text-gray-900 dark:text-gray-100 mb-2">이메일:</div>

                        <div className="flex justify-between items-center">
                            <div className="text-lg font-semibold text-gray-900 dark:text-gray-100">롤닉네임</div>
                            {editMode ? (
                                <>
                                    <input
                                        type="text"
                                        value={newLolNickname}
                                        onChange={(e) => setNewLolNickname(e.target.value)}
                                        className="text-lg font-semibold text-gray-900 dark:text-gray-100 bg-white rounded-md border border-gray-300 p-2"
                                    />
                                    <button
                                        onClick={updateLolNickname}
                                        className="inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-10 px-4 py-2 text-white bg-blue-500 hover:bg-blue-700 ml-2">
                                        Update
                                    </button>
                                    <button
                                        onClick={() => setEditMode(false)}
                                        className="inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-10 px-4 py-2 text-white bg-gray-500 hover:bg-gray-700 ml-2">
                                        Cancel
                                    </button>
                                </>
                            ) : (
                                <>
                                    <h1 className="text-lg font-semibold text-gray-900 dark:text-gray-100">{userInfo?.lolNickname}</h1>
                                    <button
                                        onClick={() => setEditMode(true)}
                                        className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 h-10 px-4 py-2 text-white bg-blue-500 hover:bg-blue-700">
                                        Edit
                                    </button>
                                </>
                            )}
                        </div>
                        <div className="mt-2 text-lg font-bold text-gray-900 dark:text-gray-100">태그:</div>

                    </div>
                    <div className="mt-8 bg-teal-300 dark:bg-teal-700 rounded-lg p-4">
                        <div className="text-lg font-semibold text-gray-900 dark:text-gray-100">성격:</div>
                        <ViewPersonalities/>
                        <div className="mt-2 text-lg font-bold text-gray-900 dark:text-gray-100"># 창의적, #적극적, #친절함</div>
                    </div>
                    <RequestSender/>

                </div>
                <div className="w-3/4 p-8">
                    <div className="grid grid-cols-1 gap-4 font-bold text-lg text-purple-600">
                        <div className="h-12  dark:bg-teal-200 rounded-lg">
                            <h1>매칭내역</h1>
                        </div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                        <div className="h-12 bg-teal-300 dark:bg-teal-700 rounded-lg"></div>
                    </div>
                </div>
            </div>

        </div>
    )





    }
