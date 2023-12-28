

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './style.css';
import SearchComponent from "../Squad";

const ProfileViewer: React.FC = () => {

    const [summonerData, setSummonerData] = useState<any>(null);
    const [leagueData, setLeagueData] = useState<any[]>([]); // 여러 개의 리그 데이터를 저장하는 배열
    const [matchData, setMatchData] = useState<any[]>([]);
    // const [spellData, setSpellData] = useState<spellData | null>(null);
    const { gameName, tagLine } = useParams<{gameName:string; tagLine:string}>();


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
    const getKoreanQueueType = (queueType:String) => {
        switch (queueType) {
            case 'RANKED_SOLO_5x5':
                return '솔로   랭크';
            case 'RANKED_FLEX_SR':
                return '자유 5:5 랭크';
            // 다른 Queue Type에 대한 경우 추가
            default:
                return queueType;
        }
    };

    // const groupedMatches: { [key: string]: any[] } = {};
    // matchData.forEach(match => {
    //     const matchId = match.metadata.matchId;
    //     if (!groupedMatches[matchId]) {
    //         groupedMatches[matchId] = [];
    //     }
    //     groupedMatches[matchId].push(match.info.participants);
    // });

    interface ExpandedRows {
        [key: number]: boolean;
    }
    const DisplayMatchData = () => {



        const [expandedRows, setExpandedRows] = useState<ExpandedRows>({});

        const filteredSummonerMatches = matchData.filter(match =>

            match.info.participants.some(

                (participant: any) => participant.summonerName === gameName

            )
        );

        const getChampionName = (match: any) => {
            if (!gameName || !match.info || !match.info.participants) {
                return ''; // gameName이 undefined이거나 match.info 또는 match.info.participants가 없는 경우 처리
            }

            const participant = match.info.participants.find(
                (participant: any) => participant.summonerName === gameName
            );

            return participant && participant.championName ? participant.championName : '';
        };

        const toggleRow = (index: number) => {
            setExpandedRows({ ...expandedRows, [index]: !expandedRows[index] });
        };


        return (
            <table>
                <thead>
                <tr style={{ display: 'flex', border: '1px solid black' }}>
                    <th className="header">승</th>
                    <td style={{ borderRight: '1px solid black', padding: '8px' }}></td>
                    <th className="header">챔피언</th>
                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}></td>
                    <th className="header">타입</th>
                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}></td>
                    <th className="header">KDA</th>
                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}></td>
                    <th className="header">S/R</th>
                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}></td>
                    <th className="header">팀</th>
                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}></td>
                    <th className="header">아이템</th>
                </tr>
                </thead>
                <tbody>
                {filteredSummonerMatches.map((match, index) => (
                    <React.Fragment key={index}>
                    <tr key={index} style={{ display:'flex' ,border: '1px solid black' }}>
                        {/*<td>{new Date(match.info.gameEndTimestamp).toLocaleString()}</td>*/}
                        {/*<td style={{ display: 'flex', padding: '10px' }}>{match.metadata.matchId}</td>*/}

                        <td style={{ borderRight: '1px solid black', padding: '10px' }}>{match.info.teams[0].win ? '승' : '패'}</td>

                        {match.info && match.info.participants && (
                            <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                <img
                                    className="ProfileImg"
                                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${getChampionName(match)}.png`}
                                    alt="Profile Icon"
                                    style={{ width: '25px', height: '25px' }}
                                />
                            </td>
                        )}
                        <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                            {match.info.queueId === 420 ? '랭 크' : match.info.queueId === 440 ? '자유 랭크' : match.info.queueId === 450 ? '총력전' : match.info.queueId === 400 ? '일반' :' '}
                        </td>
                        <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                            {match.info.participants.map((participant: any, participantIndex: number) => (
                                <div key={participantIndex}>
                                    {participant.summonerName === gameName && (
                                        <div>
                                            <p>평점({((participant.kills + participant.deaths) / participant.assists).toFixed(2)})</p>
                                            <p>{participant.kills}/{participant.deaths}/{participant.assists}</p>


                                        </div>
                                    )}
                                </div>
                            ))}
                        </td>
                        <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                            {match.info.participants.map((participant: any, participantIndex: number) => (
                                <div key={participantIndex}>
                                    {participant.summonerName === gameName && (

                                        <img
                                            className="ProfileImg"
                                            src={`/assets/${participant.summoner1Id}.png`}
                                            alt="Profile Icon"
                                            style={{ width: '25px', height: '25px' }} // 이미지 크기 조정
                                        />

                                    )}
                                    {participant.summonerName === gameName && (
                                        <img
                                            className="ProfileImg"
                                            src={`/assets/${participant.summoner2Id}.png`}
                                            alt="Profile Icon"
                                            style={{ width: '25px', height: '25px' }} // 이미지 크기 조정
                                        />
                                    )}
                                    {/*{participant.summonerName === gameName && (*/}
                                    {/*    <div>*/}
                                    {/*        {participant.perks && participant.perks.styles && participant.perks.styles.map((style: any, styleIndex: number) => (*/}
                                    {/*            <div key={styleIndex}>*/}
                                    {/*                <p>Style: {style.style}</p>*/}
                                    {/*                {style.selections && style.selections.map((selection: any, selectionIndex: number) => (*/}
                                    {/*                    <div key={selectionIndex}>*/}
                                    {/*                        <p>Perk: {selection.perk}</p>*/}

                                    {/*                    </div>*/}
                                    {/*                ))}*/}
                                    {/*            </div>*/}
                                    {/*        ))}*/}
                                    {/*    </div>*/}
                                    {/*)}*/}
                                </div>
                            ))}
                        </td>
                        <td style={{ display: 'flex', flexWrap: 'wrap', padding: '10px', borderRight: '1px solid black' }}>
                            {match.info.participants.map((participant: any, participantIndex: number) => (
                                <div key={participantIndex} style={{ margin: '', width: '10%' }}>
                                    {participant.championName === 'FiddleSticks' ? (
                                        <img
                                            className="ProfileImg"
                                            src={`/assets/${participant.championName}.png`}
                                            alt="Custom Profile Icon"
                                            style={{ width: '25px', height: '25px' }}
                                        />
                                    ) : (
                                        <img
                                            className="ProfileImg"
                                            src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                            alt="Profile Icon"
                                            style={{ width: '25px', height: '25px' }}
                                        />
                                    )}
                                </div>
                            ))}
                        </td>
                        <td style={{ display: 'flex', flexWrap: 'wrap', padding: '10px',borderRight: '1px solid black' }}>
                            {match.info.participants
                                .filter((participant: any) => participant.summonerName === gameName)
                                .map((participant: any, participantIndex: number) => (
                                    participant.summonerName === gameName && (
                                        <React.Fragment key={participantIndex}>
                                            {[
                                                participant.item0,
                                                participant.item1,
                                                participant.item2,
                                                participant.item3,
                                                participant.item4,
                                                participant.item5,
                                                participant.item6,
                                            ].map((item, index) => (
                                                item !== 0 && (
                                                    <img
                                                        key={index}
                                                        src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${item}.png`}
                                                        alt={`Item ${index}`}
                                                        style={{ width: '25px', height: '25px', marginRight: '5px' }}
                                                    />
                                                )
                                            ))}
                                        </React.Fragment>
                                    )
                                ))}
                        </td>

                        <td style={{ width: '60px', padding: '10px' }}>
                            <button
                                onClick={() => toggleRow(index)}
                                style={{ fontSize: '10px' }}
                            >
                                {expandedRows[index] ? '숨기기' : '더보기'}
                            </button>
                        </td>
                        {/* 여기서 챔피언 정보를 추가하세요 */}
                        {/* match.info.participants에서 챔피언 정보 가져와서 표시 */}
                    </tr>
                        {expandedRows[index] && (
                            <tr key={`details-${index}`} style={{ display: 'flex', border: '1px solid black' }}>
                                <td colSpan={3}>
                                    <table>
                                        <thead>
                                        <tr>
                                            <th>승리팀</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                {match.info.teams[0].win
                                                    ? match.info.participants
                                                        .filter((participant: any) => participant.teamId === match.info.teams[0].teamId)
                                                        .map((participant: any, i: number) => (
                                                            <table key={i} style={{ border: '1px solid black' }}>

                                                                <tbody>
                                                                <td>
                                                                    {participant.championName === 'FiddleSticks' ? (
                                                                        <img
                                                                            src={`/assets/${participant.championName}.png`}
                                                                            alt="Custom Champion Image"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    ) : (
                                                                        <img
                                                                            src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                                            alt="Champion"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    )}
                                                                </td>

                                                                    <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                        {participant.summonerName}
                                                                    </td>
                                                                    <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                        <img
                                                                            src={`/assets/${participant.summoner1Id}.png`}
                                                                            alt="summoner1Id"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                        <img
                                                                            src={`/assets/${participant.summoner2Id}.png`}
                                                                            alt="summoner2Id"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />

                                                                    </td>


                                                                    <td style={{ borderRight: '1px solid black', padding: '10px' }}>

                                                                        {participant.summonerLevel}
                                                                    </td>

                                                                    <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                        <p>평점 {((participant.kills + participant.deaths) / participant.assists).toFixed(2)}</p>
                                                                        <p>{participant.kills}/{participant.deaths}/{participant.assists}</p>
                                                                    </td>

                                                                    <td style={{ display: 'flex' }}>
                                                                        {[
                                                                            participant.item0,
                                                                            participant.item1,
                                                                            participant.item2,
                                                                            participant.item3,
                                                                            participant.item4,
                                                                            participant.item5,
                                                                            participant.item6,
                                                                        ].map((itemId, i) => (
                                                                            itemId !== 0 && ( // Check if itemId exists (not 0)
                                                                                <img
                                                                                    key={i}
                                                                                    src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${itemId}.png`}
                                                                                    alt={`Item ${i}`}
                                                                                    style={{ width: '25px', height: '25px', marginRight: '5px' }}
                                                                                />
                                                                            )
                                                                        ))}
                                                                    </td>

                                                                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                        <p>딜량</p>
                                                                        {participant.totalDamageDealtToChampions}
                                                                    </td>

                                                                    <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                        <p>피해량</p>
                                                                        {participant.totalDamageTaken}
                                                                    </td>




                                                                {/* 다른 필드 추가 */}
                                                                </tbody>
                                                            </table>
                                                        ))
                                                    : match.info.participants
                                                        .filter((participant: any) => participant.teamId !== match.info.teams[0].teamId)
                                                        .map((participant: any, i: number) => (
                                                            <table key={i} style={{ border: '1px solid black' }}>

                                                                <tbody>
                                                                <td>
                                                                    {participant.championName === 'FiddleSticks' ? (
                                                                        <img
                                                                            src={`/assets/${participant.championName}.png`}
                                                                            alt="Custom Champion Image"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    ) : (
                                                                        <img
                                                                            src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                                            alt="Champion"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    )}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    {participant.summonerName}
                                                                </td>
                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <img
                                                                        src={`/assets/${participant.summoner1Id}.png`}
                                                                        alt="summoner1Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />
                                                                    <img
                                                                        src={`/assets/${participant.summoner2Id}.png`}
                                                                        alt="summoner2Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />

                                                                </td>


                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>

                                                                    {participant.summonerLevel}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <p>평점 {((participant.kills + participant.deaths) / participant.assists).toFixed(2)}</p>
                                                                    <p>{participant.kills}/{participant.deaths}/{participant.assists}</p>
                                                                </td>

                                                                <td style={{ display: 'flex' }}>
                                                                    {[
                                                                        participant.item0,
                                                                        participant.item1,
                                                                        participant.item2,
                                                                        participant.item3,
                                                                        participant.item4,
                                                                        participant.item5,
                                                                        participant.item6,
                                                                    ].map((itemId, i) => (
                                                                        itemId !== 0 && ( // Check if itemId exists (not 0)
                                                                            <img
                                                                                key={i}
                                                                                src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${itemId}.png`}
                                                                                alt={`Item ${i}`}
                                                                                style={{ width: '25px', height: '25px', marginRight: '5px' }}
                                                                            />
                                                                        )
                                                                    ))}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>딜량</p>
                                                                    {participant.totalDamageDealtToChampions}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>피해량</p>
                                                                    {participant.totalDamageTaken}
                                                                </td>




                                                                {/* 다른 필드 추가 */}
                                                                </tbody>
                                                            </table>
                                                        ))}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <br />
                                    <table>
                                        <thead>
                                        <tr>
                                            <th>패배팀</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                {!match.info.teams[0].win
                                                    ? match.info.participants
                                                        .filter((participant: any) => participant.teamId === match.info.teams[0].teamId)
                                                        .map((participant: any, i: number) => (
                                                            <table key={i} style={{ border: '1px solid black' }}>

                                                                <tbody>
                                                                <td>
                                                                    {participant.championName === 'FiddleSticks' ? (
                                                                        <img
                                                                            src={`/assets/${participant.championName}.png`}
                                                                            alt="Custom Champion Image"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    ) : (
                                                                        <img
                                                                            src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                                            alt="Champion"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    )}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    {participant.summonerName}
                                                                </td>
                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <img
                                                                        src={`/assets/${participant.summoner1Id}.png`}
                                                                        alt="summoner1Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />
                                                                    <img
                                                                        src={`/assets/${participant.summoner2Id}.png`}
                                                                        alt="summoner2Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />

                                                                </td>


                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>

                                                                    {participant.summonerLevel}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <p>평점 {((participant.kills + participant.deaths) / participant.assists).toFixed(2)}</p>
                                                                    <p>{participant.kills}/{participant.deaths}/{participant.assists}</p>
                                                                </td>

                                                                <td style={{ display: 'flex' }}>
                                                                    {[
                                                                        participant.item0,
                                                                        participant.item1,
                                                                        participant.item2,
                                                                        participant.item3,
                                                                        participant.item4,
                                                                        participant.item5,
                                                                        participant.item6,
                                                                    ].map((itemId, i) => (
                                                                        itemId !== 0 && ( // Check if itemId exists (not 0)
                                                                            <img
                                                                                key={i}
                                                                                src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${itemId}.png`}
                                                                                alt={`Item ${i}`}
                                                                                style={{ width: '25px', height: '25px', marginRight: '5px' }}
                                                                            />
                                                                        )
                                                                    ))}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>딜량</p>
                                                                    {participant.totalDamageDealtToChampions}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>피해량</p>
                                                                    {participant.totalDamageTaken}
                                                                </td>




                                                                {/* 다른 필드 추가 */}
                                                                </tbody>
                                                            </table>
                                                        ))
                                                    : match.info.participants
                                                        .filter((participant: any) => participant.teamId !== match.info.teams[0].teamId)
                                                        .map((participant: any, i: number) => (
                                                            <table key={i} style={{ border: '1px solid black' }}>

                                                                <tbody>
                                                                <td>
                                                                    {participant.championName === 'FiddleSticks' ? (
                                                                        <img
                                                                            src={`/assets/${participant.championName}.png`}
                                                                            alt="Custom Champion Image"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    ) : (
                                                                        <img
                                                                            src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${participant.championName}.png`}
                                                                            alt="Champion"
                                                                            style={{ width: '25px', height: '25px' }}
                                                                        />
                                                                    )}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    {participant.summonerName}
                                                                </td>
                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <img
                                                                        src={`/assets/${participant.summoner1Id}.png`}
                                                                        alt="summoner1Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />
                                                                    <img
                                                                        src={`/assets/${participant.summoner2Id}.png`}
                                                                        alt="summoner2Id"
                                                                        style={{ width: '25px', height: '25px' }}
                                                                    />

                                                                </td>


                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>

                                                                    {participant.summonerLevel}
                                                                </td>

                                                                <td style={{ borderRight: '1px solid black', padding: '10px' }}>
                                                                    <p>평점 {((participant.kills + participant.deaths) / participant.assists).toFixed(2)}</p>
                                                                    <p>{participant.kills}/{participant.deaths}/{participant.assists}</p>
                                                                </td>

                                                                <td style={{ display: 'flex' }}>
                                                                    {[
                                                                        participant.item0,
                                                                        participant.item1,
                                                                        participant.item2,
                                                                        participant.item3,
                                                                        participant.item4,
                                                                        participant.item5,
                                                                        participant.item6,
                                                                    ].map((itemId, i) => (
                                                                        itemId !== 0 && ( // Check if itemId exists (not 0)
                                                                            <img
                                                                                key={i}
                                                                                src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/${itemId}.png`}
                                                                                alt={`Item ${i}`}
                                                                                style={{ width: '25px', height: '25px', marginRight: '5px' }}
                                                                            />
                                                                        )
                                                                    ))}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>딜량</p>
                                                                    {participant.totalDamageDealtToChampions}
                                                                </td>

                                                                <td style={{ borderLeft: '1px solid black', padding: '10px' }}>
                                                                    <p>피해량</p>
                                                                    {participant.totalDamageTaken}
                                                                </td>




                                                                {/* 다른 필드 추가 */}
                                                                </tbody>
                                                            </table>
                                                        ))}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        )}
            </React.Fragment>
                ))}

                </tbody>
            </table>
        );
    };



    return (
        <div id = 'Summoner'>
            <div className = "Look">
                {summonerData &&(
                    <div>
                        <div className="flex items-center justify-center ">
                            <SearchComponent />
                        </div>
                    </div>
                )}
            </div>
            <div className = "SummonerBody">

                <div className = "SideContent" >
                {summonerData && (
                    <div>
                        <img className = "ProfileImg"
                             src={`http://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${summonerData.profileIconId}.png`}
                             alt="Profile Icon"
                             style={{ width: '100px', height: '100px' }}
                        />

                        <p>
                            {/*Profile Icon ID: {summonerData.profileIconId} <br />*/}
                            소환사명: {summonerData.name} <br />
                            레벨: {summonerData.summonerLevel} <br />

                        </p>

                    </div>
                )}

                        {leagueData   && (


                            <div>
                                {/* leagueData.map을 통해 데이터를 렌더링 */}
                                {leagueData.map((data) => (
                                    <div key={data.leagueId}>
                                        {/*<p>League ID: {data.leagueId}</p>*/}
                                        <p>Queue Type:  {getKoreanQueueType(data.queueType)}</p>
                                        <p>티어: {data.tier} {data.rank}</p>
                                        {/* tierImages 객체를 사용하여 tier에 맞는 이미지를 화면에 출력 */}
                                        <img className="RankIcon" src={require(`../../views/Summoner/assets/${data.tier}.png`)} alt={data.tier}
                                             style={{ width: '100px', height: '100px' }}/>
                                        {/* 나머지 데이터 출력 */}
                                        <p>LP: {data.leaguePoints}</p>
                                        <p>승리: {data.wins}</p>
                                        <p>패배: {data.losses}</p>
                                        {/* 필요한 다른 데이터 출력 */}
                                    </div>
                                ))}
                            </div>







                        )}
                </div>

                <div className="RealContent">


                    {/*<table>*/}
                    {/*    <thead>*/}
                    {/*    <tr>*/}
                    {/*        <th className="header">Match ID</th>*/}
                    {/*        <th className="header">승리 여부</th>*/}
                    {/*        <th className="header">챔피언</th>*/}
                    {/*    </tr>*/}
                    {/*    </thead>*/}

                    {/*</table>*/}
                    {DisplayMatchData()}
                </div>
            </div>
        </div>
    );
    };

export default ProfileViewer;


