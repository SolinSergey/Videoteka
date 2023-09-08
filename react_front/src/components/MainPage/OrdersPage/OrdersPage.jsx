import "./OrdersPage.css";
import axios from "axios";
import React, {Component} from "react";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import ModalWindow from "../../../widgets/ModalWindow/ModalWindow";
import {toast, ToastContainer} from "react-toastify";


class OrdersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            saleFilms: [],
            rentFilms: [],
            isSale: false,
            youtubeID: 'Zzrh35vCfeI',
            modalActive: false

        }
    }

    componentDidMount() {
        this.getRentedOrders()
        this.getSoldOrders()
    }

    changeHandler = () => {
        if (this.state.modalActive === true) {
            this.setState({modalActive: false})
        } else {
            this.setState({modalActive: true})
        }

    }
    getRentedOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/rent')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        rentFilms: data,
                    })
                }).catch(reason => console.log(reason))
        } catch (e) {
            console.log(e)
        }

    }
    getSoldOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/sale')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        saleFilms: data,
                    })
                })
        } catch (e) {
            console.log(e)
        }

    }


    render() {
        let displayCartNotification = (message) => {
            toast.error(message, {toastId: 'e1'});
        }
        async function buyRentedFilm(id, title, cover, price, rentPrice) {
            try {
                const response = await axios.get('http://localhost:5555/cart/api/v1/cart/add',
                    {
                        params: {
                            uuid: localStorage.getItem('guestCartId'),
                            filmId: id,
                            filmTitle: title,
                            salePrice: price,
                            rentPrice: rentPrice,
                            filmImageUrlLink: cover,
                           isSale: true
                        }
                    }
                ) .then(response => {
                    window.location = '/cart'
                    displayCartNotification(response.data.value)
                },  function errorCallback(response) {
                    console.log(response)
                    let displayCartNotification = (message) => {
                        toast.error(message, {toastId: 'e1'});
                    }
                    displayCartNotification(response.response.data.value)
                })
                console.log("Ответ метода buyRentedFilm: " + response.data)

            } catch (e) {
                let displayCartNotification = (message) => {
                    toast.error(message, {toastId: 'e2'});
                }
                displayCartNotification(e.value)
            }
        }

        let showVideo = () => {
            return <iframe
                src="https://vk.com/video_ext.php?oid=-210183487&id=456241708&hash=f71b473e86dbfd54" width="640"
                height="360" frameBorder="0" allowFullScreen="1"
                allow="autoplay; encrypted-media; fullscreen; picture-in-picture">

            </iframe>
            // <iframe className='video'
            //          title='Youtube player'
            //          sandbox='allow-same-origin allow-forms allow-popups allow-scripts allow-presentation'
            //          src={`https://youtube.com/embed/${this.state.youtubeID}?autoplay=0`}>
            //  </iframe>

        }

        return (
            <div className={'orders_container'}>
                <div className={'rent_box__container'}>
                    <span>Арендованные фильмы</span>


                    <div className={'rent_box'}>

                        <TableContainer component={Paper}>
                            <Table stickyHeader={true} sx={{minWidth: 850}} aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}>Обложка</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}} align="center">Название</TableCell>

                                        <TableCell sx={{background: '#2b303b', color: 'white'}} align="center">Старт
                                            аренды</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}} align="center">Финиш
                                            аренды</TableCell>

                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>

                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>

                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.rentFilms.map((row) => (
                                        <TableRow
                                            hover role="checkbox"
                                            tabIndex={-1}
                                            key={row.username}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}

                                        >
                                            <TableCell component="th" scope="row">
                                                <div className={'orders_img'}>
                                                    <img src={row.imageUrlLink} alt={'обложка'}/>
                                                </div>
                                            </TableCell>
                                            <TableCell align="center">{row.filmTitle}</TableCell>

                                            <TableCell
                                                align="center">{new Date(row.rentStart).toLocaleString("ru-RU", {
                                                day: "numeric",
                                                month: "long",
                                                year: "numeric",
                                                hour: "numeric",
                                                minute: "numeric",
                                            })}</TableCell>
                                            <TableCell
                                                align="center">{new Date(row.rentEnd).toLocaleString("ru-RU", {
                                                day: "numeric",
                                                month: "long",
                                                year: "numeric",
                                                hour: "numeric",
                                                minute: "numeric",
                                            })}</TableCell>

                                            <TableCell align="center">
                                                <button className={'buy-order__btn'}
                                                        onClick={() => this.setState({modalActive: true})}>Смотреть
                                                </button>
                                            </TableCell>
                                            <TableCell align="center">
                                                <button className={'buy-order__btn'}
                                                        onClick={() => buyRentedFilm(row.filmId, row.filmTitle, row.imageUrlLink, row.salePrice, row.rentPrice)}>Купить
                                                </button>
                                            </TableCell>

                                            <TableCell align="right">

                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>

                </div>

                <div className={'rent_box__container'}>
                    <span>Ваши фильмы</span>

                    <div className={'own_box'}>
                        <TableContainer component={Paper}>
                            <Table stickyHeader={true} sx={{minWidth: 850}} aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{
                                            background: '#2b303b',
                                            color: 'white',
                                            maxWidth: 50
                                        }}>Обложка</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white', maxWidth: 77}}
                                                   align="center">Название</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>


                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.saleFilms.map((row) => (
                                        <TableRow
                                            hover role="checkbox"
                                            tabIndex={-1}
                                            key={row.username}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row">
                                                <div className={'orders_img'}>
                                                <img src={row.imageUrlLink}/>
                                                </div>
                                            </TableCell>
                                            <TableCell align="center">{row.filmTitle}</TableCell>
                                            <TableCell align="center">
                                                <button className={'buy-order__btn'}
                                                        onClick={() => this.setState({modalActive: true})}>Смотреть
                                                </button>
                                            </TableCell>

                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>

                </div>
                <ModalWindow active={this.state.modalActive}
                             setActive={this.changeHandler}
                >
                    {showVideo()}
                </ModalWindow>
                <ToastContainer
                    position="top-right"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="dark"
                />
            </div>


        )
    }
}

export default OrdersPage;