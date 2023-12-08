import { create } from 'zustand';
import LoginUser from "../types/login-user.interface";

interface UserStore {
    user: LoginUser | null;
    setUser: (user: LoginUser | null) => void;
}

const useUserStore = create<UserStore>(set => ({
    user: null,
    setUser: (user: LoginUser | null) => {set((state) => ({ ...state, user }))},
}));

export default useUserStore;



