import ItemButton from "../elements/ItemButton.tsx";
import {useEffect, useState} from "react";
import {chooseItem} from "../../../../api/game.service.ts";
import {ResultResponse} from "../game.interface.enum.ts";

interface ItemBarProps {
    sessionId: string;
    isActive: boolean;
    items: string[];
    onGameUpdate: (updatesList: ResultResponse[]) => void;
    itemUsed?: string;
}

function ItemsBar({sessionId, isActive, items, onGameUpdate, itemUsed}: ItemBarProps) {
    const [loading, setLoading] = useState(false);
    const [clickedIndex, setClickedIndex] = useState<number | null>(null);

    const handleItemClick = async (item: string, index: number) => {
        setClickedIndex(index);
        setLoading(true);

        try {
            const responsePromise = chooseItem(sessionId, item);

            await new Promise(resolve => setTimeout(resolve, 2000));

            const response = await responsePromise;
            onGameUpdate(response.data);
        } catch (error) {
            console.error("Item use failed", error);
        } finally {
            setClickedIndex(null);
            setLoading(false);
        }
    };

    useEffect(() => {
        if (itemUsed && items.includes(itemUsed)) {
            const usedIndex = items.findIndex(item => item.toLowerCase() === itemUsed.toLowerCase());
            setClickedIndex(usedIndex);

        } else {
            setClickedIndex(null);
        }
    }, [itemUsed, items]);


    return (
        <div
            className={`no-margin centered-row-container items-bar-${isActive ? "active" : "non-active"} ${loading ? "non-clickable" : ""}`}>
            {items.map((item, index) => (
                <ItemButton
                    key={index}
                    onClick={() => handleItemClick(item.toLowerCase(), index)}
                    item={`${item}`}
                    animate={clickedIndex === index}
                />
            ))}
        </div>
    );
}


export default ItemsBar;