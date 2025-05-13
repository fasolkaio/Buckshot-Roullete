import './styles/App.css'
import MenuBuckshotRoulette from "./components/game/buckshot-roulette/containers/MenuBuckshotRoulette.tsx";

function App() {
    const loggedPlayer = 'lina';
    return (
    <>
        <MenuBuckshotRoulette player={loggedPlayer}/>
        <div>
            <h1>Services</h1>
        </div>
    </>
    )
}

export default App
