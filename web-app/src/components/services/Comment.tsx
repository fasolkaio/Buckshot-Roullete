import { useEffect, useState } from "react";
import { ServicesProps, Comment as CommentInterface } from "./services.interfaces.ts";
import { addComment, getComments } from "../../api/comment.service.ts";

function Comments({ player, game }: ServicesProps) {
    const [comments, setComments] = useState<CommentInterface[]>([]);
    const [newComment, setNewComment] = useState("");
    const [loading, setLoading] = useState(false);

    const fetchComments = async () => {
        try {
            const res = await getComments(game);
            setComments(res.data.slice(-20).reverse());
        } catch (err) {
            console.error("Error fetching comments", err);
        }
    };

    const handleAddComment = async () => {
        if (!newComment.trim()) return;
        setLoading(true);
        try {
            await addComment(game, player, newComment);
            setNewComment("");
            fetchComments();
        } catch (err) {
            console.error("Error posting comment", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchComments();
    }, [game]);

    return (
        <div className="container comment-container">
            <h1 className="title">Comments</h1>

            {player !== "GUEST" ? (
                <div className="comment-input">
                    <textarea
                        rows={3}
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                        placeholder="Leave a comment..."
                    />
                    <button
                        onClick={handleAddComment}
                        disabled={loading || !newComment.trim()}
                    >
                        {loading ? "Sending..." : "Add Comment"}
                    </button>
                </div>
            ) : (
                <p className="guest-message">Login to leave comments.</p>
            )}

            <div className="comments-list">
                {comments.map((c, idx) => (
                    <div key={idx} className="comment-block">
                        <div className="player-info">
                            <span>{c.player}</span>
                            <span>{`${c.commentedOn.slice(1,10)} ${c.commentedOn.slice(11,16)}`}</span>
                        </div>
                        <div className="comment-text">
                            {c.comment}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Comments;