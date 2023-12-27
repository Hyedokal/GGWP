

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';


const ProfileViewer: React.FC = () => {

    const [summonerData, setSummonerData] = useState<any>(null);
    const [leagueData, setLeagueData] = useState<any[]>([]); // 여러 개의 리그 데이터를 저장하는 배열
    const [matchData, setMatchData] = useState<any[]>([]);
    // const [spellData, setSpellData] = useState<spellData | null>(null);
    const { gameName, tagLine } = useParams();


    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.post('http://localhost:8000/search-service/v1/summoner/get', {
                    gameName: gameName,
                    tagLine: tagLine
                });

                if (response.status === 200) {
                    setSummonerData(response.data.data); // 새로운 데이터 설정
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        const fetchLeagueData = async () => {
            try {
                const response = await axios.post('http://localhost:8000/search-service/v1/league/get', {
                    gameName: gameName,
                    tagLine: tagLine
                });
                if (response.status === 200) {
                    setLeagueData(response.data.data); // 받아온 리그 데이터 설정
                }
            } catch (error) {
                console.error('Error fetching league data:', error);
            }
        };
        const fetchMatchData = async () => {
            try {
                const response = await axios.post('http://localhost:8000/search-service/v1/match/get', {
                    gameName: gameName,
                    tagLine: tagLine
                });
                setMatchData(response.data.data); // 가져온 데이터 설정
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };



        // 컴포넌트가 마운트되면 파일 읽기 함수 호출

     // 빈 배열은 컴포넌트가 마운트될 때 한 번만 호출됨



        fetchData();
        fetchLeagueData();
        fetchMatchData();
    }, [gameName,tagLine]);



    const [filteredMatchData, setFilteredMatchData] = useState<any[]>([]);

    const filterMatchesByQueue = (queueId: number) => {
        const filteredMatches = matchData.filter(match => match.info.queueId === queueId);
        setFilteredMatchData(filteredMatches);
    };
    const showAllMatches = () => {
        setFilteredMatchData(matchData);
    };

    // const groupedMatches: { [key: string]: any[] } = {};
    // matchData.forEach(match => {
    //     const matchId = match.metadata.matchId;
    //     if (!groupedMatches[matchId]) {
    //         groupedMatches[matchId] = [];
    //     }
    //     groupedMatches[matchId].push(match.info.participants);
    // });




    return (
        <div id = 'Summoner'>
            <div className = "Look">
                {summonerData &&(
                    <div>

                    </div>
                )}
            </div>
            <div className = "SummonerBody">

                <div className = "SideContent">
                {summonerData && (
                    <div>
                        <img className = "ProfileImg"
                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${summonerData.profileIconId}.png`}
                             alt="Profile Icon"
                             style={{ width: '100px', height: '100px' }}
                        />

                        <p>
                            {/*Profile Icon ID: {summonerData.profileIconId} <br />*/}
                            Summoner Name: {summonerData.name} <br />
                            Summoner Level: {summonerData.summonerLevel} <br />

                        </p>

                    </div>
                )}

                        {leagueData   && (
                            // <div>
                            //     <h2>League Data</h2>
                            //     {leagueData.map((data, index) => (
                            //         <div key={index}>
                            //             <p>Queue Type: {data.queueType}</p>
                            //             <p>Tier: {data.tier}</p>
                            //             <p>Rank: {data.rank}</p>
                            //             <p>League Points: {data.leaguePoints}</p>
                            //             {/* 다른 필드들도 출력 */}
                            //             <img
                            //
                            //                 src={tierImages[data.tier]}
                            //                 alt={data.tier}
                            //             />
                            //         </div>


                            <div>
                                {/* leagueData.map을 통해 데이터를 렌더링 */}
                                {leagueData.map((data) => (
                                    <div key={data.leagueId}>
                                        {/*<p>League ID: {data.leagueId}</p>*/}
                                        <p>Queue Type: {data.queueType}</p>
                                        <p>Tier: {data.tier}</p>
                                        {/* tierImages 객체를 사용하여 tier에 맞는 이미지를 화면에 출력 */}
                                        <img className="RankIcon" src={require(`../../views/Test/assets/${data.tier}.png`)} alt={data.tier} />
                                        {/* 나머지 데이터 출력 */}
                                        <p>League Points: {data.leaguePoints}</p>
                                        <p>Wins: {data.wins}</p>
                                        <p>Losses: {data.losses}</p>
                                        {/* 필요한 다른 데이터 출력 */}
                                    </div>
                                ))}
                            </div>







                        )}
                </div>

                <div className="RealContent">


                    <table>
                        <thead>
                        <tr>
                            <th colSpan={2}>
                                <button onClick={showAllMatches}>전체</button>
                            </th>
                        <th colSpan={2}>
                                <button onClick={() => filterMatchesByQueue(420)}>솔로랭크</button>
                            </th>
                        <th colSpan={2}>
                                <button onClick={() => filterMatchesByQueue(440)}>자유랭크</button>
                            </th>
                        </tr>

                        </thead>
                        <tbody style={{ fontSize: '9px' }}>
                        {Object.values(
                            filteredMatchData.reduce((acc: any, match) => {
                                if (!acc[match.metadata.matchId]) {
                                    acc[match.metadata.matchId] = {
                                        matchId: match.metadata.matchId,
                                        queueId: match.info.queueId,
                                        teams: {
                                            100: [],
                                            200: [],
                                        },
                                    };
                                }
                                match.info.participants.forEach((participant: any) => {
                                    acc[match.metadata.matchId].teams[participant.teamId].push(participant);
                                });
                                return acc;
                            }, {})
                        ).map((matchGroup: any, index: number) => (
                            <tr key={index}>
                                <React.Fragment>
                                    <td colSpan={2}>Match ID: {matchGroup.matchId}</td>
                                    {/* 100팀의 정보 출력 */}
                                    <td style={{ border: '1px solid black', padding: '5px' }}>
                                        {matchGroup.teams[100].map((participant: any, pIndex: number) => (
                                            <div key={`${index}-100-${pIndex}`} style={{ marginBottom: '10px' }}>
                                                <div>{participant.summonerName}</div>
                                                <div>{participant.teamId}</div>
                                                <img
                                                    className="champion"
                                                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                    alt="champion"
                                                    style={{ width: '30px', height: '30px' }}
                                                />
                                                {/* Add other participant details here */}
                                            </div>
                                        ))}
                                    </td>
                                    {/* 200팀의 정보 출력 */}
                                    <td style={{ border: '1px solid black', padding: '5px' }}>
                                        {matchGroup.teams[200].map((participant: any, pIndex: number) => (
                                            <div key={`${index}-200-${pIndex}`} style={{ marginBottom: '10px' }}>
                                                <div>{participant.summonerName}</div>
                                                <div>{participant.teamId}</div>
                                                <img
                                                    className="champion"
                                                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                    alt="champion"
                                                    style={{ width: '30px', height: '30px' }}
                                                />
                                                {/* Add other participant details here */}
                                            </div>
                                        ))}
                                    </td>
                                </React.Fragment>
                            </tr>
                        ))}
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    );
    };

export default ProfileViewer;



// import React, { useEffect, useState } from 'react';
//  import axios from 'axios';
//
// const ProfileViewer: React.FC = () => {
// const [summonerData, setSummonerData] = useState<any>(null);
// const [leagueData, setLeagueData] = useState<any[]>([]);
// const [matchData, setMatchData] = useState<any[]>([]);
// const [spellData, setSpellData] = useState<any>(null);
// const fetchData = async () => {
//     try {
//         const response = await axios.post('http://localhost:8000/search-service/v1/summoner/get', {
//             gameName: '쌈오징어',
//             tagLine: 'KR1'
//         });
//         if (response.status === 200) {
//             setSummonerData(response.data.data);
//         }
//     } catch (error) {
//         console.error('Error fetching data:', error);
//     }
// };
// const fetchLeagueData = async () => {
//     try {
//         const response = await axios.post('http://localhost:8000/search-service/v1/league/get', {
//             gameName: '쌈오징어',
//             tagLine: 'KR1'
//         });
//         if (response.status === 200) {
//             setLeagueData(response.data.data);
//         }
//     } catch (error) {
//         console.error('Error fetching league data:', error);
//     }
// };
// const fetchMatchData = async () => {
//     try {
//         const response = await axios.post('http://localhost:8000/search-service/v1/match/get', {
//             gameName: '쌈오징어',
//             tagLine: 'KR1'
//         });
//         setMatchData(response.data.data);
//     } catch (error) {
//         console.error('Error fetching data:', error);
//     }
// };
//     const readJsonFile = async (summonerId: number) => {
//         try {
//             const response = await fetch('../json/summoner.json');
//             const jsonData = await response.json();
//             const spellData = jsonData[summonerId];
//             setSpellData(spellData); // Promise 대신 실제 값을 상태에 저장
//         } catch (error) {
//             console.error('파일 읽기 오류:', error);
//         }
//     };
//
// useEffect(() => {
//     fetchData();
//     fetchLeagueData();
//     fetchMatchData();
//      // 이 부분이 이전에 말씀드린대로 여러 번 호출되는 문제가 있을 수 있습니다.
// }, []);
//     useEffect(() => {
//         if (spellData) {
//             const summonerId = spellData.id;
//             readJsonFile(summonerId).then((data) => {
//                 setSpellData(data); // 받은 데이터를 상태에 업데이트
//             });
//         }
//     }, [spellData]);
//
//     // 나머지 코드








// {/*<div className= "RealContent">*/}
// {/*        {matchData.map((match, index) => (*/}
// {/*            <div key={index}>*/}
// {/*                /!*<h2>Match {index + 1}</h2>*!/*/}
// {/*                <ul>*/}
// {/*                    {match.info.participants.map((participant: any, pIndex: number) => (*/}
// {/*                        <li key={pIndex}>*/}
// {/*                            /!*<p>Participant ID: {participant.participantId}</p>*!/*/}
// {/*                            <p>  {participant.summonerId}</p>*/}
// {/*                            /!*<p> {participant.profileIcon}</p>*!/*/}
// {/*                            <img className = "ChampionImg"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${participant.profileIcon}.png`}*/}
// {/*                                 alt="Champion Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            /!*<p>PUUID: {participant.puuid}</p>*!/*/}
// {/*                            <p>Summoner Level: {participant.summonerLevel}</p>*/}
// {/*                            <p>Summoner Name: {participant.summonerName}</p>*/}
// {/*                            /!*<p>Riot ID Name: {participant.riotIdName}</p>*!/*/}
// {/*                            /!*<p>Riot ID Tagline: {participant.riotIdTagline}</p>*!/*/}
// {/*                            <p>Kills: {participant.kills}</p>*/}
// {/*                            <p>Assists: {participant.assists}</p>*/}
// {/*                            <p>Deaths: {participant.deaths}</p>*/}
// {/*                            <p>Champ Experience: {participant.champExperience}</p>*/}
// {/*                            <p>Champ Level: {participant.champLevel}</p>*/}
// {/*                            /!*<p>Champion ID: {participant.championId}</p>*!/*/}
// {/*                            <img className = "ChampionImg"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}*/}
// {/*                                 alt="Champion Img"*/}
// {/*                                 style={{ width: '60px', height: '60px' }}*/}
// {/*                            />*/}
// {/*                            <p>Champion Name: {participant.championName}</p>*/}
// {/*                            <p>Item0: {participant.item0}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item0}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Item1: {participant.item1}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item1}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Item2: {participant.item2}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item2}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Item3: {participant.item3}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item3}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Item4: {participant.item4}</p>*/}
// {/*                            {participant.item4 === 0 ? (*/}
// {/*                                <img*/}
// {/*                                    className="ItemImg0"*/}
// {/*                                    src={require(`../../views/Test/assets/0.png`)}*/}
// {/*                                    alt="Item2Img0"*/}
// {/*                                    style={{ width: '50px', height: '50px' }}*/}
// {/*                                />*/}
// {/*                            ) : (*/}
// {/*                                <img*/}
// {/*                                    className="ItemImg0"*/}
// {/*                                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item2}.png`}*/}
// {/*                                    alt="Item2Img"*/}
// {/*                                    style={{ width: '50px', height: '50px' }}*/}
// {/*                                />*/}
// {/*                            )}*/}
// {/*                            <p>Item5: {participant.item5}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item5}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Item6: {participant.item6}</p>*/}
// {/*                            <img className = "ItemImg0"*/}
// {/*                                 src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item6}.png`}*/}
// {/*                                 alt="Item0Img"*/}
// {/*                                 style={{ width: '50px', height: '50px' }}*/}
// {/*                            />*/}
// {/*                            <p>Summoner1 ID: {participant.summoner1Id}</p>*/}
//
// {/*                            <p>Summoner2 ID: {participant.summoner2Id}</p>*/}
// {/*                            <p>Perks Styles: {participant.perks.styles[0].style}</p>*/}
// {/*                            <p>Neutral Minions Killed: {participant.neutralMinionsKilled}</p>*/}
// {/*                            <p>Total Minions Killed: {participant.totalMinionsKilled}</p>*/}
// {/*                            <p>Total Damage Dealt To Champions: {participant.totalDamageDealtToChampions}</p>*/}
// {/*                            <p>Total Damage Taken: {participant.totalDamageTaken}</p>*/}
// {/*                            <p>Team ID: {participant.teamId}</p>*/}
// {/*                            <p>Team Position: {participant.teamPosition}</p>*/}
// {/*                        </li>*/}
// {/*                    ))}*/}
// {/*                </ul>*/}
// {/*            </div>*/}
// {/*        ))}*/}
// {/*</div>*/}




// <td>
//     {participant.perks.styles.map((style: any, styleIndex: any) => (
//         <div key={`${index}-${pIndex}-${styleIndex}`}>
//             <p>Style: {style.style}</p>
//             {/* 다른 Perks 정보 */}
//         </div>
//     ))}
// </td>

                            //
                            //     {participant.item4 === 0 ? (
                            //     <img
                            //         className="ItemImg0"
                            //         src={require(`../../views/Test/assets/0.png`)}
                            //         alt="Item2Img0"
                            //         style={{ width: '50px', height: '50px' }}
                            //     />
                            // ) : (
                            //     <img
                            //         className="ItemImg0"
                            //         src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item2}.png`}
                            //         alt="Item2Img"
                            //         style={{ width: '50px', height: '50px' }}
                            //     />
                            // )}

//
// {/*<tbody>*/}
// {/*{Object.values(*/}
// {/*    filteredMatchData.reduce((acc: any, match) => {*/}
// {/*        match.info.participants.forEach((participant: any) => {*/}
// {/*            if (!acc[match.metadata.matchId]) {*/}
// {/*                acc[match.metadata.matchId] = {*/}
// {/*                    matchId: match.metadata.matchId,*/}
// {/*                    queueId: match.info.queueId,*/}
// {/*                    participants: [],*/}
// {/*                };*/}
// {/*            }*/}
// {/*            acc[match.metadata.matchId].participants.push(participant);*/}
// {/*        });*/}
// {/*        return acc;*/}
// {/*    }, {})*/}
// {/*).map((matchGroup: any, index: number) => (*/}
// {/*    <tr key={index}>*/}
// {/*        <td>{matchGroup.matchId}</td>*/}
// {/*        <td>{matchGroup.queueId}</td>*/}
// {/*        {matchGroup.participants.map((participant: any, pIndex: number) => (*/}
// {/*            <React.Fragment key={`${index}-${pIndex}`}>*/}
// {/*                <td>{participant.profileIcon}</td>*/}
// {/*                <td>*/}
// {/*                <img*/}
// {/*                    className="ChampionImg"*/}
// {/*                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${participant.profileIcon}.png`}*/}
// {/*                    alt="Champion Img"*/}
// {/*                    style={{ width: '50px', height: '50px' }}*/}
// {/*                />*/}
// {/*                </td>*/}
// {/*                <td>{participant.summonerLevel}</td>*/}
// {/*                <td>{participant.summonerName}</td>*/}
// {/*                <td>{participant.kills}</td>*/}
// {/*                <td>{participant.assists}</td>*/}
// {/*                <td>{participant.deaths}</td>*/}
// {/*                <td>{participant.champExperience}</td>*/}
// {/*                <td>{participant.champLevel}</td>*/}
// {/*                <td>{participant.championId}</td>*/}
// {/*                <td>{participant.championName}</td>*/}
// {/*                <td>*/}
// {/*                <img*/}
// {/*                    className="champion"*/}
// {/*                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}*/}
// {/*                    alt="champion"*/}
// {/*                    style={{ width: '50px', height: '50px' }}*/}
// {/*                />*/}
// {/*                </td>*/}
// {/*                /!* 여기에 다른 participant 정보들도 추가할 수 있어요 *!/*/}
// {/*            </React.Fragment>*/}
// {/*        ))}*/}
// {/*    </tr>*/}
// {/*))}*/}
// {/*</tbody>*/}



//
// <tbody>
// {filteredMatchData.map((match, index) => (
//     <React.Fragment key={index}>
//         {match.info.participants.map((participant: { participantId: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; summonerId: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; profileIcon: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; puuid: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; summonerLevel: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; summonerName: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; riotIdName: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; riotIdTagline: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; kills: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; assists: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; deaths: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; champExperience: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; champLevel: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; championId: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; championName: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item0: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item1: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item2: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item3: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item4: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item5: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; item6: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; summoner1Id: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; summoner2Id: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; perks: { styles: { style: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; }[]; }; neutralMinionsKilled: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; totalMinionsKilled: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; totalDamageDealtToChampions: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; totalDamageTaken: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; teamId: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; teamPosition: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal | null | undefined; }, pIndex: any) => (
//             <tr key={`${index}-${pIndex}`}>
//                 <td>{match.metadata.matchId}</td>
//                 <td>{match.info.queueId}</td>
//                 {/*<td>{participant.participantId}</td>*/}
//                 {/*<td>{participant.summonerId}</td>*/}
//                 <td>{participant.profileIcon}</td>
//                 <td>
//                     <img className = "ChampionImg"
//                          src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${participant.profileIcon}.png`}
//                          alt="Champion Img"
//                          style={{ width: '50px', height: '50px' }}
//                     />
//                 </td>
//                 {/*<td>{participant.puuid}</td>*/}
//                 <td>{participant.summonerLevel}</td>
//                 <td>{participant.summonerName}</td>
//
//                 {/*<td>{participant.riotIdName}</td>*/}
//                 {/*<td>{participant.riotIdTagline}</td>*/}
//                 {/*<td>{participant.kills}</td>*/}
//                 {/*<td>{participant.assists}</td>*/}
//                 {/*<td>{participant.deaths}</td>*/}
//                 {/*<td>{participant.champExperience}</td>*/}
//                 {/*<td>{participant.champLevel}</td>*/}
//                 {/*<td>{participant.championId}</td>*/}
//                 <td>{participant.championName}</td>
//                 <td>
//                     <img className = "champion"
//                          src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
//                          alt="champion"
//                          style={{ width: '50px', height: '50px' }}
//                     />
//                 </td>
//                 <td>{participant.item0}</td>
//                 <td>
//                     {participant.item0 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item0}.png`}
//                             alt="Item0Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//
//                 <td>{participant.item1}</td>
//                 <td>
//                     {participant.item1 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item1}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.item2}</td>
//                 <td>
//                     {participant.item2 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item2}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.item3}</td>
//                 <td>
//                     {participant.item3 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item3}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.item4}</td>
//                 <td>
//                     {participant.item4 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item4}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.item5}</td>
//
//                 <td>
//                     {participant.item5 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item5}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.item6}</td>
//                 <td>
//                     {participant.item6 === 0 ? (
//                         <img
//                             className="ItemImg0"
//                             src={require(`../../views/Test/assets/0.png`)}
//                             alt="Item2Img0"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     ) : (
//                         <img
//                             className="ItemImg0"
//                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${participant.item6}.png`}
//                             alt="Item2Img"
//                             style={{ width: '50px', height: '50px' }}
//                         />
//                     )}
//                 </td>
//                 <td>{participant.summoner1Id}</td>
//                 <td>
//                     <img src={require(`../../views/Test/assets/${participant.summoner1Id}.png`)}
//                          alt= "item6"
//                          style={{ width: '50px', height: '50px' }}/>
//                 </td>
//
//                 <td>{participant.summoner2Id}</td>
//                 <td>
//                     <img className = "summoner2Id"
//                          src={require(`../../views/Test/assets/${participant.summoner2Id}.png`)}
//                          alt="summoner2Id"
//                          style={{ width: '50px', height: '50px' }}
//                     />
//                 </td>
//                 <td>{participant.perks.styles[0].style}</td>
//                 {/*<td>*/}
//                 {/*    {participant.perks.styles.map((style: any, styleIndex: any) => (*/}
//                 {/*        <div key={`${index}-${pIndex}-${styleIndex}`}>*/}
//                 {/*            <p>Style: {style.style}</p>*/}
//                 {/*            /!* 다른 Perks 정보 *!/*/}
//                 {/*        </div>*/}
//                 {/*    ))}*/}
//                 {/*</td>*/}
//                 <td>{participant.neutralMinionsKilled}</td>
//                 <td>{participant.totalMinionsKilled}</td>
//                 <td>{participant.totalDamageDealtToChampions}</td>
//                 <td>{participant.totalDamageTaken}</td>
//
//                 <td>{participant.teamId}</td>
//                 <td>{participant.teamPosition}</td>
//             </tr>
//         ))}
//     </React.Fragment>
// ))}
// </tbody>


// <tbody style={{ fontSize: '9px' }}>
// {Object.values(
//     filteredMatchData.reduce((acc: any, match) => {
//         match.info.participants.forEach((participant: any) => {
//             if (!acc[match.metadata.matchId]) {
//                 acc[match.metadata.matchId] = {
//                     matchId: match.metadata.matchId,
//                     queueId: match.info.queueId,
//                     participants: [],
//                 };
//             }
//             acc[match.metadata.matchId].participants.push(participant);
//         });
//         return acc;
//     }, {})
// ).map((matchGroup: any, index: number) => (
//     <tr key={index}>
//
//         {matchGroup.participants.map((participant: any, pIndex: number) => (
//             <React.Fragment key={`${index}-${pIndex}`}>
//
//                 <td>{participant.summonerName}</td>
//                 <td>
//                     <img className = "champion"
//                          src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
//                          alt="champion"
//                          style={{ width: '50px', height: '50px' }}
//                     />
//                 </td>
//
//
//                 {/* Add other participant details here */}
//             </React.Fragment>
//         ))}
//     </tr>
// ))}
// </tbody>