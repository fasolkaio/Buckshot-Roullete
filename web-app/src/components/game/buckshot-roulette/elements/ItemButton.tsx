interface ItemButtonProps {
    onClick: () => void;
    item: string;
    animate?: boolean;
}

function ItemButton({onClick, item, animate = false}: ItemButtonProps) {
    const url = `/src/assets/${item}${animate ? '' : '-shadow'}.png`;

    return (
        <button
            onClick={onClick}
            className={`item-button ${animate ? 'levitate glow-background' : ''}`}
        >
            <img src={url} alt={item}/>
        </button>
    );
}

export default ItemButton;
