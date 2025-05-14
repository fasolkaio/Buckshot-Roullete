import { ServicesProps } from "./services.interfaces.ts";
import { getAverageRating, getRating, setRating } from "../../api/rate.service.ts";
import { useEffect, useState } from "react";

const Rating = ({ game, player }: ServicesProps) => {
    const [averageRating, setAverageRating] = useState<number>(0);
    const [userRating, setUserRating] = useState<number | null>(null);
    const [hoverRating, setHoverRating] = useState<number>(0);

    useEffect(() => {
        const fetchAverageRating = async () => {
            try {
                const response = await getAverageRating(game);
                if (response.data && typeof response.data === 'number') {
                    setAverageRating(response.data);
                } else {
                    setAverageRating(0);
                }
                if(player != 'GUEST'){
                    const responseUserRating = await getRating(game, player);
                    if (responseUserRating.data && typeof responseUserRating.data === 'number') {
                        setUserRating(responseUserRating.data);
                    } else {
                        setUserRating(0);
                    }
                }
            } catch (error) {
                console.error("Error fetching average rating", error);
            }
        };

        const fetchUserRating = async () => {
            if (player !== "GUEST") {
                try {
                    const response = await getRating(game, player);
                    if (response.data.rating) {
                        setUserRating(response.data.rating);
                    }
                } catch (error) {
                    console.error("Error fetching user rating", error);
                }
            }
        };

        fetchAverageRating();
        fetchUserRating();
    }, [game, player]);

    const handleRatingClick = (rating: number) => {
        if (player !== "GUEST") {
            setUserRating(rating);
            setRating(game, player, rating)
                .then(() => getAverageRating(game))
                .then((response) => {
                    setAverageRating(response.data);
                })
                .catch((error) => {
                    console.error("Error submitting rating", error);
                });
        }
    };

    const renderAverageStars = () => {
        const fullStars = Math.floor(averageRating);
        return (
            <>
                {[...Array(fullStars)].map((_, i) => (
                    <span key={i} className={`star filled`}>
                        ★
                    </span>
                ))}
            </>
        );
    };

    const renderUserStars = () => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            const isFilled = i <= (userRating ?? 0);
            const isHovered = i <= hoverRating;

            stars.push(
                <button
                    key={i}
                    className={`star ${isFilled ? "filled" : ""} ${isHovered ? "hovered" : ""}`}
                    onClick={() => handleRatingClick(i)}
                    onMouseEnter={() => setHoverRating(i)}
                    onMouseLeave={() => setHoverRating(0)}
                >
                    ★
                </button>
            );
        }
        return stars;
    };

    return (
        <div className="container">
            <h1 className="title">Game Rating</h1>
            <div className="average-rating">
                <div className="stars">{renderAverageStars()}</div>
            </div>
            {player !== "GUEST" && (
                <>
                    <h3>Rate game: </h3>
                    <div className="user-rating">
                        <div className="stars">{renderUserStars()}</div>
                    </div>
                </>
            )}
        </div>
    );
};

export default Rating;
