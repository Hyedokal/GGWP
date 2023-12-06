import { create } from 'zustand';
import UserInfo from "../types/user.interface";

interface  userInfoStore {
    userInfo    : UserInfo | null;
    setUserInfo : (user: UserInfo | null) => void;
}

const UserInfoStore = create<userInfoStore>(set => ({
    userInfo: null,
    setUserInfo: (userInfo: UserInfo | null) => {set((state) => ({ ...state, userInfo }))},
}));


export default UserInfoStore;