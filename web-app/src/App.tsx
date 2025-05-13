import './styles/App.css'
import './styles/services.css'
import MenuBuckshotRoulette from "./components/game/buckshot-roulette/containers/MenuBuckshotRoulette.tsx";
import TopScore from "./components/services/TopScore.tsx";
import Rating from "./components/services/Rating.tsx";

function App() {
    const loggedPlayer = 'lina';
    return (
    <>
        <MenuBuckshotRoulette player={loggedPlayer}/>
        <div className={"service-container scroll-box"}>
            <Rating player={loggedPlayer} game={'buckshot-roulette'}/>
            <TopScore game={'buckshot-roulette'} player={loggedPlayer}/>
        </div>
    </>
    )
}

export default App
