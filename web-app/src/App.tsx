import './styles/App.css'
import './styles/services.css'
import MenuBuckshotRoulette from "./components/game/buckshot-roulette/containers/MenuBuckshotRoulette.tsx";
import TopScore from "./components/services/TopScore.tsx";
import Rating from "./components/services/Rating.tsx";
import Comment from "./components/services/Comment.tsx";
import Header from "./components/Header.tsx";

function App() {
    const loggedPlayer = 'lina';
    return (
    <>
        <Header player={loggedPlayer} login={()=>{}} singIn={()=>{}}/>
        <div className={"content"}>
            <MenuBuckshotRoulette player={loggedPlayer}/>
            <div className={"service-container"}>
                <div className={"rating-score-container"}>
                    <Rating player={loggedPlayer} game={'buckshot-roulette'}/>
                    <TopScore player={loggedPlayer} game={'buckshot-roulette'}/>
                </div>
                <Comment player={loggedPlayer} game={'buckshot-roulette'}></Comment>
            </div>
        </div>

    </>
    )
}

export default App
