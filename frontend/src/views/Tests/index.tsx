import {useNavigate, useParams} from "react-router-dom";
import UserInfoStore from "../../stores/userInfo.store";
import {useCookies} from "react-cookie";
import {useState} from "react";
import SquadResponseDto from "../../apis/dto/response/squad/SquadResponseDto";

export default function Test() {
    // description: 게시물 번호 상태 //
    const { boardNumber } = useParams();

    const {userInfo} = UserInfoStore();
    // description: Cookie 상태 //
    const [cookie, setCookies] = useCookies();

    // description: 게시물 정보 상태 //
    const [board, setBoard] = useState<SquadResponseDto | null>(null);

    const navigator = useNavigate();

    return (
        <div>
        </div>
    );
}
