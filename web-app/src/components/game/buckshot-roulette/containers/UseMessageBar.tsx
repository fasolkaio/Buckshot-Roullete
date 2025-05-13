interface UseMassageProps {
    item: string;
    result: string;
}

function UseMessageBar({item, result}: UseMassageProps) {
    const bulletType = result == 'BULLET_WAS_LIVE' ? 'live' : 'blank';
    return <div className={"message-container"}>
        <h2>{item.toLowerCase() === 'beer' ? 'Thrown bullet was' : 'Next bullet is'} {bulletType.toUpperCase()}</h2>
    </div>
}

export default UseMessageBar;