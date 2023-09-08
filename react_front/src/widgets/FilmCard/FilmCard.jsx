import style from './FilmCard.module.css'
import {Rating} from "@mui/material";
import TagButton from "../TagButton/TagButton";
import {useState} from "react";
import ModalWindow from "../ModalWindow/ModalWindow";
import FilmPage from "../../components/MainPage/FilmPage/FilmPage";
import axios from "axios";
import ReviewsPage from "../../components/MainPage/ReviewsPage/ReviewsPage";
import AddReviewPage from "../../components/MainPage/AddReviewPage/AddReviewPage";


function FilmCard(props) {
    const [ratingState, setRatingState] = useState('');
    console.log('Id фильма: ' + props.id)
    let getRating = () => {
        try {
            axios.get('http://localhost:5555/catalog/api/v1/rating/total_film_rating',
                {
                    params: {
                        filmId: props.id,
                    }
                }
            ).then(response => response.data)
                .then(data => {
                    let rating = data;

                    setRatingState(rating)
                })

        } catch (e) {

        }
    }
    let switchScene = (command) => {
        switch (command) {
            case 'reviews':
                return <ReviewsPage filmId={props.id}
                                    title={props.title}
                                    setCommand={setCommand}
                />
            case 'add_review':
                return <AddReviewPage filmId={props.id}
                                      setCommand={setCommand}
                />
            default:
                return <FilmPage director={props.director}
                                 filmId={props.id}
                                 ratingValue={ratingState}
                                 isSale={props.isSale}
                                 title={props.title}
                                 premierYear={props.premierYear}
                                 country={props.country}
                                 description={props.description}
                                 genre={props.genre}
                                 rentPrice={props.rentPrice}
                                 salePrice={props.salePrice}
                                 cover={props.imageUrlLink}
                                 setCommand={setCommand}
                />
        }
    }
    console.log()
    getRating()
    const [command, setCommand] = useState("")
    const [modalActive, setModalActive] = useState(false);
    return (
        <div className={style.container}>

            <div className={style.card} onClick={() => setModalActive(true)}>
                <div className={style.poster}>
                    <img src={props.imageUrlLink} alt=""/>
                </div>

                <div className={style.details}>
                    <h2>
                        {props.title}<br/>
                        {props.director.map((director) => <span>Режисёр: {director}</span>)}

                    </h2>
                    <div className={style.rating}>
                        <Rating name="half-rating-read"
                               sx={{padding: 1}}
                                size={'normal'}
                                precision={0.01}
                                value={ratingState}
                                readOnly/>
                        <span>{ratingState}</span>

                    </div>
                    {props.genre.map((genre) => <TagButton genre={genre}/>)}

                </div>
                <div className="card-footer">
                    <div className="clearfix">
                    </div>
                </div>
            </div>
            <ModalWindow setCommand={setCommand} active={modalActive} setActive={setModalActive}>
                {switchScene(command)}
            </ModalWindow>
        </div>
    )
}

export default FilmCard;