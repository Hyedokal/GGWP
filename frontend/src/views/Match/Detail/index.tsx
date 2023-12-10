import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

interface BoardDetailResponseDto {
    myPos: string;
    wantPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    rank: string;
    memo: string;
    commentList: any[];
    updatedAt: string;
    qtype: string;
    sid: number;
}

const BoardDetail = () => {
    const { sId } = useParams();
    const [boardDetail, setBoardDetail] = useState<BoardDetailResponseDto | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [editData, setEditData] = useState<BoardDetailResponseDto | null>(null);

    useEffect(() => {
        const fetchBoardDetail = async () => {
            try {
                const response = await axios.get<BoardDetailResponseDto>(`http://localhost:8000/v1/squads/${sId}`);
                setBoardDetail(response.data);
                setEditData(response.data); // Initialize edit data
            } catch (error) {
                console.error('Error fetching board details:', error);
            }
        };

        fetchBoardDetail();
    }, [sId]);

    const toggleEditMode = () => {
        if (boardDetail) {
            setEditData(boardDetail);
            setIsEditing(!isEditing);
        }
    };

    const handleEditComplete = async () => {
        if (editData) {
            try {
                await axios.put(`http://localhost:8000/v1/squads/${sId}`, editData);
                setBoardDetail(editData);
                setIsEditing(false);
            } catch (error) {
                console.error('Error updating board details:', error);
            }
        }
    };

    if (!boardDetail) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {isEditing ? (
                <div>
                    <input
                        type="text"
                        value={editData?.myPos || ''}
                        onChange={(e) => setEditData(prevState => ({
                            ...prevState as BoardDetailResponseDto,
                            myPos: e.target.value
                        }))}
                    />
                    {/* Add more input fields for other editable properties as needed */}
                    <button onClick={handleEditComplete}>Edit Complete</button>
                </div>
            ) : (
                <div>
                    <p>myPos: {boardDetail.myPos}</p>
                    {/* Display other board details */}
                    <button onClick={toggleEditMode}>Edit</button>
                </div>
            )}
        </div>
    );
};

export default BoardDetail;
