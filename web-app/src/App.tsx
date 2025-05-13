import './styles/App.css'
import './styles/services.css'
import MenuBuckshotRoulette from "./components/game/buckshot-roulette/containers/MenuBuckshotRoulette.tsx";
import TopScore from "./components/services/TopScore.tsx";

function App() {
    const loggedPlayer = 'lina';
    return (
    <>
        <MenuBuckshotRoulette player={loggedPlayer}/>
        <div>
            <TopScore game={'buckshot-roulette'} player={loggedPlayer}/>
        </div>
    </>
    )
}

export default App
