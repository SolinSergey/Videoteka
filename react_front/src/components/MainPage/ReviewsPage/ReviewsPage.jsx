import "./ReviewsPage.css"
import React, {useEffect, useState} from "react"
import {Avatar, Divider, List, ListItem, ListItemAvatar, ListItemText, Typography} from "@mui/material";
import axios from "axios";
import {toast} from "react-toastify";

function ReviewsPage(props) {
    let role = JSON.parse(localStorage.getItem('role_user'))
    let getFilmIdReview = async () => {
        try {
            return await axios.get('http://localhost:5555/catalog/api/v1/rating/all_grade_and_review_by_filmId', {
                params: {
                    filmId: props.filmId
                }
            })
                .then((response) =>
                    setFilmReviews(response.data),
                    function errorCallback(response) {
                        console.log(response)
                        let displayCartNotification = (message) => {
                            toast.error(message);
                        }
                        displayCartNotification(response.response.data.value)
                    })
                // .then(data => {
                //
                //     axios.post('http://localhost:5555/auth/api/v1/users/adding_names_to_ratings', {data})
                //         .then((response) => setFilmReviews(response.data) )
                //
                //     }
                // )
        } catch (e) {

        }
    }
    let getFullNameReviewers = async (userId) => {
        return await axios.post('http://localhost:5555/auth/api/v1/users/fullname_by_id', {
            params: {
                userId: userId
            }
        }).then(r => {
            setFullName(r.data.value)
        })

    }
    const handleChange = (command) => {
        switch (command) {
            case '':
                props.setCommand(command)
                break
            case 'add_review':
                props.setCommand(command)
                break
            default:
                return null
        }
    }
    useEffect(() => {
    getFilmIdReview()
    },[])

    const [fullName, setFullName] = useState('')
    const [filmReviews, setFilmReviews] = useState([]);



    return (
        <div className={'review-page'}>
            {filmReviews.length > 0?
                <List sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>
                    {filmReviews.map((txt) => {
                        // getFullNameReviewers(txt.user_id)
                        return (
                        <ListItem alignItems="flex-start">
                            <ListItemAvatar>
                                <Avatar alt="Remy Sharp" src="https://mui.com/static/images/avatar/3.jpg"/>
                            </ListItemAvatar>
                            <ListItemText
                                primary={props.title}
                                secondary={
                                    <React.Fragment>

                                        <Typography
                                            sx={{display: 'inline'}}
                                            component="span"
                                            variant="subtitle2"
                                            color="text.primary"
                                        >
                                            {/*{fullName}*/}
                                        </Typography>

                                        <Divider variant="inset" component="li"/>
                                        <span>{txt.review}</span>
                                    </React.Fragment>
                                }
                            />
                        </ListItem>
                            )})}
                    <Divider variant="inset" component="li"/>
                </List>
                :
                <div className={'review_empty__sheet'}>
                    <p>У этого фильма ещё нет рецензий.</p>
                    <Divider variant="middle" />
                    Станьте первым рецензентом!
                </div>
            }

            <div className={'review-page__btn_group'}>
                <div className={'button-back_box'}>
                    <button onClick={() => handleChange('')}>Вернуться к фильму</button>
                </div>
                {role === 'ROLE_USER'?
                <div className={'button-back_box'}>
                    <button onClick={() => handleChange('add_review')}>Написать отзыв</button>
                </div>
                    :
                    null
                }
            </div>
        </div>
    )

}

export default ReviewsPage