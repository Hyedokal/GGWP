import {useEffect, useState} from "react";
import axios from "axios";

interface SquadMatchResponseDto {
    myPos: string;
    wantPos: string;
    summonerName: string;
    rank: string;
    commentList: Comment[] | null;
    updatedAt: string;
    qtype: string;
    smic: boolean;
    smemo: string;
    sid: number;
}

export default function Test() {
    const [posts, setPosts] = useState<SquadMatchResponseDto[]>([]); // Initialize with an empty array
    const [editingPostId, setEditingPostId] = useState<number | null>(null);
    const [editData, setEditData] = useState<SquadMatchResponseDto | null>(null);

    const startEdit = (post: SquadMatchResponseDto) => {
        setEditingPostId(post.sid);
        setEditData(post);
    };

    const cancelEdit = () => {
        setEditingPostId(null);
        setEditData(null);
    };

    const handleEditChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>, field: keyof SquadMatchResponseDto) => {
        if (editData) {
            setEditData({ ...editData, [field]: e.target.value });
        }
    };

    const updatePost = async () => {
        if (editData && editingPostId) {
            try {
                const response = await axios.put(`http://localhost:8000/v1/squads/${editingPostId}`, editData);
                console.log(response);
                // Refresh the posts list
                fetchPosts();
                // Reset editing state
                setEditingPostId(null);
                setEditData(null);
            } catch (error) {
                console.error('Error updating post:', error);
            }
        }
    };
    const fetchPosts = async () => {
        try {
            const response = await axios.get('http://localhost:8000/v1/squads');
            setPosts(response.data);
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    };

    useEffect(() => {
        fetchPosts();
    }, []); // model에서 sendPostRequest요청을 보낼떄마다 useEffect가 실행되면서 posts에 새로운 데이터가 들어가게 된다.

    return (
        <div>
            {posts.map((post) => (
                <div key={post.sid}>
                    {editingPostId === post.sid ? (
                        // Render input fields for editing
                        <div>
                            {/* Add input/select fields for each editable field */}
                            <input type="text" value={editData?.summonerName || ''} onChange={(e) => handleEditChange(e, 'summonerName')} />
                            {/* Add more fields as needed */}
                            <button onClick={updatePost}>Edit Complete</button>
                            <button onClick={cancelEdit}>Cancel</button>
                        </div>
                    ) : (
                        // Render post details
                        <div>
                            <p>Summoner Name: {post.summonerName}</p>
                            {/* Render other post details */}
                            <button onClick={() => startEdit(post)}>Edit</button>
                        </div>
                    )}
                    <hr />
                </div>
            ))}
        </div>
    );
}