import {create} from 'zustand';
import CidInfo from "./cid.interface";


interface  cidStore {
    cidInfo    : CidInfo | null;
    setCidInfo : (cid:CidInfo | null) => void;
}


const useCidInfoStore = create<cidStore>(set => ({
    cidInfo: null,
    setCidInfo: (cidInfo: CidInfo | null) => {set((state) => ({ ...state, cidInfo }))},
}));

export default useCidInfoStore;
