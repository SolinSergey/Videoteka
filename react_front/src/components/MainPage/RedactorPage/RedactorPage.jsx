import style from "./RedactorPage.module.css"
import React, {useEffect, useState} from "react";
import {Checkbox, FormControl, Input, InputLabel, ListItemText, MenuItem, Select} from "@mui/material";
import axios from "axios";
import {PlusIcon} from "primereact/icons/plus";
import {toast, ToastContainer} from "react-toastify";
import ListCard from "../../../widgets/ListCard/ListCard";
import ListCardDeleteable from "../../../widgets/ListCardDeleteable/ListCardDeleteable";
// import {Checkbox, Input} from "antd";

// Long id;
// String title;
// Integer premierYear;
// String description;
// String imageUrlLink;
// List<String> genre;
// List<String> country;
// List<String> director;
// Integer rentPrice;
// Integer salePrice;
const RedactorPage = () => {

    let getAllGenres = () => {
        axios.get("http://localhost:5555/catalog/api/v1/genre/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                setGenres(data)
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }

    async function getAllFilms() {
        try {
            return await axios.get('http://localhost:5555/catalog/api/v1/film/titles')
                .then((response) => {
                        setFilms(response.data)
                    },
                    function errorCallback(response) {
                        console.log(response)
                        let displayCartNotification = (message) => {
                            toast.error(message);
                        }
                        displayCartNotification(response.response.data.value)
                    })
        } catch (e) {

        }
    }

    let getAllDirectors = () => {
        axios.get('http://localhost:5555/catalog/api/v1/director/all')
            .then(r => r.data)
            .then(data => {
                setDirectors(data)
            })
    }
    let getAllCountries = () => {
        axios.get('http://localhost:5555/catalog/api/v1/country/all')
            .then(r => r.data)
            .then(data => {
                setCountries(data)
            })
    }
    useEffect(() => {
        getAllFilms()
        getAllDirectors()
        getAllGenres()
        getAllCountries()
    }, [])
    let displayCartNotification = (message) => {
        toast.success(message);
    };
    let addNewFilm = () => {
        axios.post('http://localhost:5555/catalog/api/v1/film/new_film', {
            id: filmId,
            title: filmTitle,
            premierYear: filmDate,
            description: filmDescription,
            imageUrlLink: linkToCover,
            genre: postGenresList,
            country: postCountriesList,
            director: postDirectorList,
            rentPrice: filmRentPrice,
            salePrice: filmSalePrice

        }).then(response => displayCartNotification(response.data.value))
            .then((data) => {
                getAllFilms()
            }, function errorCallback(response) {
                console.log(response)
                let displayCartNotification = (message) => {
                    toast.error(message, {toastId: 'e1'});
                }
                displayCartNotification(response.response.data.value)
            })
    }

    let changeDirectorsStateHandler = (event) => {
        setPostDirectorList(event.target.value)
    }


    let changeCountriesStateHandler = (event) => {
        setPostCountriesList(event.target.value)

    }
    let changeGenresStateHandler = (event) => {
        setPostGenresList(event.target.value)
    }


    let addLinkToCover = (event) => {
        setLinkToCover(event.target.value)
    }

    function handleFilmsChange(event) {
        console.log(event.target.value)
        if (event.target.value === "Добавить фильм") {
            setFilmId(0)
            setLinkToCover('')
            setFilmTitle('')
            setFilmDate(2023)
            setFilmDescription('')
            setFilmSalePrice(119)
            setFilmRentPrice(19)
            setPostGenresList([])
            setPostDirectorList([])
            setPostCountriesList([])
            setOpen(true)
        } else {
            if (event.target.value !== "Добавить фильм") {
                setOpen(false)
                try {
                    return axios.get('http://localhost:5555/catalog/api/v1/film/id', {
                        params: {
                            id: event.target.value
                        }
                    }).then(response => response.data)
                        .then((data) => {
                            setFile(data)
                            setFilmId(data.id)
                            setLinkToCover(data.imageUrlLink)
                            setFilmTitle(data.title)
                            setFilmDate(data.premierYear)
                            setFilmDescription(data.description)
                            setFilmSalePrice(data.salePrice)
                            setFilmRentPrice(data.rentPrice)
                            setPostGenresList(data.genre)
                            setPostDirectorList(data.director)
                            setPostCountriesList(data.country)
                        })
                } catch (e) {

                }
            }
        }
    }

    const [genres, setGenres] = useState([])
    const [filmId, setFilmId] = useState(0)
    const [directors, setDirectors] = useState([])
    const [countries, setCountries] = useState([])
    const [open, setOpen] = useState(true)
    const [openCountryMenu, setOpenCountryMenu] = useState(false)
    const [films, setFilms] = useState([])
    const [file, setFile] = useState([])
    const [linkToCover, setLinkToCover] = useState('')
    const [filmTitle, setFilmTitle] = useState('')
    const [filmDescription, setFilmDescription] = useState('')
    const [filmDate, setFilmDate] = useState(0)
    const [filmRentPrice, setFilmRentPrice] = useState(0)
    const [filmSalePrice, setFilmSalePrice] = useState(0)
    const [postDirectorList, setPostDirectorList] = useState([])
    const [postCountriesList, setPostCountriesList] = useState([])
    const [postGenresList, setPostGenresList] = useState([])
    const [checked, setChecked] = useState(false)


    function addFilmTitle(event) {
        setFilmTitle(event.target.value)
    }

    function addFilmDescription(event) {
        setFilmDescription(event.target.value)
    }

    function addPremierYear(event) {
        setFilmDate(event.target.value)
    }

    function addRentPrice(event) {
        setFilmRentPrice(event.target.value)
    }

    function addSalePrice(event) {
        setFilmSalePrice(event.target.value)
    }

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
            },
        },
    };

    return (
        <div className={style.redactor_container}>
            <h3>
                Редактирование карточки фильма
            </h3>
            <div className={style.redactor__box}>
                <div className={style.redactor__img_container}>
                    <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                        <InputLabel id="demo-select-small-label">Фильмы</InputLabel>
                        <Select
                            labelId="demo-select-small-label"
                            id="demo-select-small"
                            name={'films'}
                            value={''}
                            color={'success'}
                            sx={{backgroundColor: 'white', borderColor: "success"}}
                            label="Age"
                            onChange={(event) => handleFilmsChange(event)}
                        >
                            <MenuItem value="Добавить фильм">
                                <em><PlusIcon/>Добавить фильм</em>
                            </MenuItem>
                            {films.map((film) =>
                                <MenuItem
                                    value={film.id}>{film.title}</MenuItem>
                            )}
                        </Select>
                    </FormControl>
                </div>
                {open ?
                    <div className={style.details}>
                        <div className={style.title_input}>
                            <div className={style.input_container}>
                                <input type="text" required="" value={linkToCover} placeholder={'Ссылка для обложки'}
                                       onChange={addLinkToCover}/>

                            </div>
                            <div className={style.input_container}>
                                <input type="text" required="" value={filmTitle} placeholder={'Название фильма'}
                                       onChange={addFilmTitle}/>

                            </div>
                        </div>
                        <div className={style.description_area}>
                            <textarea name="Text1" cols="40" rows="5" value={filmDescription} placeholder={'Содержание'}
                                      onChange={addFilmDescription}/>
                        </div>
                        <div className={style.details_box}>
                            <div className={style.input_container}>
                                <input type="number" required="" value={filmDate} placeholder={'Год премьеры'}
                                       onChange={addPremierYear}/>
                                <label>Год премьеры</label>
                            </div>
                            <div className={style.input_container}>
                                <input type="number" required="" value={filmRentPrice} placeholder={'Цена аренды'}
                                       onChange={addRentPrice}/>
                                <label>Цена аренды</label>
                            </div>
                            <div className={style.input_container}>
                                <input type="number" required="" value={filmSalePrice} placeholder={'Цена продажи'}
                                       onChange={addSalePrice}/>
                                <label>Цена продажи</label>
                            </div>
                        </div>

                        <div className={style.option_box}>
                            <div className={style.director_box}>
                                <span>Режиссёры</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>
                                        {postDirectorList?.map((new_director) => <ListCardDeleteable
                                            msg={new_director}/>)}
                                    </div>
                                    <div className={style.right_board}>
                                        <FormControl className={style.formControl} focused={false}>

                                            <Select
                                                labelId="demo-mutiple-checkbox-label"
                                                id="demo-mutiple-checkbox"
                                                multiple
                                                value={postDirectorList}
                                                onChange={changeDirectorsStateHandler}
                                                input={<Input/>}
                                                placeholder={'Режиссёры'}
                                                sx={{backgroundColor: 'white', color: 'black'}}
                                                renderValue={(selected) => {
                                                    if (selected.length === 0) {
                                                        return <em>Placeholder</em>;
                                                    }

                                                    return selected.join(', ');
                                                }
                                                }
                                                MenuProps={MenuProps}
                                            >
                                                <MenuItem disabled value="">
                                                    <em>Режиссёры</em>
                                                </MenuItem>
                                                {directors.map((name) => (
                                                    <MenuItem key={name.id}
                                                              value={name.firstName + ' ' + name.lastName}>
                                                        <Checkbox
                                                            checked={postDirectorList.indexOf(name.firstName + ' ' + name.lastName) > -1}/>
                                                        <ListItemText primary={name.firstName + ' ' + name.lastName}/>
                                                    </MenuItem>
                                                ))}
                                            </Select>
                                        </FormControl>
                                    </div>
                                </div>
                            </div>

                            <div className={style.director_box}>
                                <span>Страны</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>

                                        {postCountriesList?.map((new_country) => <ListCardDeleteable
                                            msg={new_country}/>)}
                                    </div>

                                    <div className={style.right_board}>
                                        <FormControl className={style.formControl} focused={false}>

                                            <Select
                                                labelId="demo-mutiple-checkbox-label"
                                                id="demo-mutiple-checkbox"
                                                multiple
                                                value={postCountriesList}
                                                onChange={changeCountriesStateHandler}
                                                input={<Input/>}
                                                placeholder={'Режиссёры'}
                                                sx={{backgroundColor: 'white', color: 'black'}}
                                                renderValue={(selected) => {
                                                    if (selected.length === 0) {
                                                        return <em>Placeholder</em>;
                                                    }

                                                    return selected.join(', ');
                                                }
                                                }
                                                MenuProps={MenuProps}
                                            >
                                                <MenuItem disabled value="">
                                                    <em>Режиссёры</em>
                                                </MenuItem>
                                                {countries.map((name) => (
                                                    <MenuItem key={name.id}
                                                              value={name.title}>
                                                        <Checkbox
                                                            checked={postCountriesList.indexOf(name.title) > -1}/>
                                                        <ListItemText primary={name.title}/>
                                                    </MenuItem>
                                                ))}
                                            </Select>
                                        </FormControl>

                                    </div>
                                </div>
                            </div>
                            <div className={style.director_box}>
                                <span>Жанры</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>
                                        {postGenresList?.map((new_genre) => <ListCardDeleteable
                                            msg={new_genre}/>)}
                                    </div>
                                    <div className={style.right_board}>
                                        <FormControl className={style.formControl} focused={false}>

                                            <Select
                                                labelId="demo-mutiple-checkbox-label"
                                                id="demo-mutiple-checkbox"
                                                multiple
                                                value={postGenresList}
                                                onChange={changeGenresStateHandler}
                                                input={<Input/>}
                                                placeholder={'Режиссёры'}
                                                sx={{backgroundColor: 'white', color: 'black'}}
                                                renderValue={(selected) => {
                                                    if (selected.length === 0) {
                                                        return <em>Placeholder</em>;
                                                    }

                                                    return selected.join(', ');
                                                }
                                                }
                                                MenuProps={MenuProps}
                                            >
                                                <MenuItem disabled value="">
                                                    <em>Жанры</em>
                                                </MenuItem>
                                                {genres.map((name) => (
                                                    <MenuItem key={name.id}
                                                              value={name.title}>
                                                        <Checkbox
                                                            checked={postGenresList.indexOf(name.title) > -1}/>
                                                        <ListItemText primary={name.title}/>
                                                    </MenuItem>
                                                ))}
                                            </Select>
                                        </FormControl>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div className={style.send_new_film__btn}>
                            <button onClick={() => addNewFilm()}>Сохранить</button>
                        </div>
                    </div>
                    :
                    <div className={style.details}>

                        <div className={style.details}>
                            <div className={style.title_input}>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={linkToCover} onChange={addLinkToCover}/>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={filmTitle} onChange={addFilmTitle}/>
                                </div>
                            </div>
                            <div className={style.description_area}>
                                <textarea name="Text1" cols="40" rows="5" value={filmDescription}
                                          onChange={addFilmDescription}/>
                            </div>
                            <div className={style.details_box}>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={filmDate} onChange={addPremierYear}/>
                                    <label>Год премьеры</label>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={filmRentPrice} onChange={addRentPrice}/>
                                    <label>Цена аренды</label>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={filmSalePrice} onChange={addSalePrice}/>
                                    <label>Цена продажи</label>
                                </div>
                            </div>
                            <div className={style.select_box}>
                                <div className={style.option_box}>
                                    <div className={style.country}>
                                        <div className={style.input_container}>
                                            <div className={style.director_box}>
                                                <span>Страны</span>
                                                <div className={style.board_box}>
                                                    <div className={style.left_board}>
                                                        {postCountriesList?.map((country) => <ListCardDeleteable
                                                            msg={country}/>)}
                                                    </div>
                                                    <div className={style.right_board}>
                                                        <FormControl className={style.formControl} focused={false}>

                                                            <Select
                                                                labelId="demo-mutiple-checkbox-label"
                                                                id="demo-mutiple-checkbox"
                                                                multiple
                                                                value={postCountriesList}
                                                                onChange={changeCountriesStateHandler}
                                                                input={<Input/>}
                                                                placeholder={'Страны'}
                                                                sx={{backgroundColor: 'white', color: 'black'}}
                                                                renderValue={(selected) => {
                                                                    if (selected.length === 0) {
                                                                        return <em>Placeholder</em>;
                                                                    }

                                                                    return selected.join(', ');
                                                                }
                                                                }
                                                                MenuProps={MenuProps}
                                                            >
                                                                <MenuItem disabled value="">
                                                                    <em>Страны</em>
                                                                </MenuItem>
                                                                {countries.map((name) => (
                                                                    <MenuItem key={name.id}
                                                                              value={name.title}>
                                                                        <Checkbox
                                                                            checked={postCountriesList.indexOf(name.title) > -1}/>
                                                                        <ListItemText primary={name.title}/>
                                                                    </MenuItem>
                                                                ))}
                                                            </Select>
                                                        </FormControl>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className={style.director}>

                                        <div className={style.input_container}>
                                            <div className={style.director_box}>
                                                <span>Режиссёры</span>
                                                <div className={style.board_box}>
                                                    <div className={style.left_board}>
                                                        {postDirectorList?.map((new_director) => <ListCardDeleteable
                                                            msg={new_director}/>)}
                                                    </div>
                                                    <div className={style.right_board}>
                                                        <FormControl className={style.formControl}>
                                                            <Select
                                                                labelId="demo-mutiple-checkbox-label"
                                                                id="demo-mutiple-checkbox"
                                                                multiple
                                                                value={postDirectorList}
                                                                onChange={changeDirectorsStateHandler}
                                                                input={<Input/>}
                                                                renderValue={(selected) => selected.join(', ')}
                                                                MenuProps={MenuProps}
                                                            >
                                                                <MenuItem disabled value="">
                                                                    <em>Режиссёры</em>
                                                                </MenuItem>
                                                                {directors.map((name) => (
                                                                    <MenuItem key={name.id}
                                                                              value={name.firstName + ' ' + name.lastName}>
                                                                        <Checkbox
                                                                            checked={postDirectorList.indexOf(name.firstName + ' ' + name.lastName) > -1}/>
                                                                        <ListItemText
                                                                            primary={name.firstName + ' ' + name.lastName}/>
                                                                    </MenuItem>
                                                                ))}
                                                            </Select>
                                                        </FormControl>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div className={style.director_box}>
                                        <span>Жанры</span>
                                        <div className={style.board_box}>
                                            <div className={style.left_board}>

                                                {postGenresList?.map((new_genre) => <ListCardDeleteable
                                                    msg={new_genre}/>)}
                                            </div>
                                            <div className={style.right_board}>
                                                <FormControl className={style.formControl} focused={false}>

                                                    <Select
                                                        labelId="demo-mutiple-checkbox-label"
                                                        id="demo-mutiple-checkbox"
                                                        multiple
                                                        value={postGenresList}
                                                        onChange={changeGenresStateHandler}
                                                        input={<Input/>}
                                                        placeholder={'Жанры'}
                                                        sx={{backgroundColor: 'white', color: 'black'}}
                                                        renderValue={(selected) => {
                                                            if (selected.length === 0) {
                                                                return <em>Placeholder</em>;
                                                            }

                                                            return selected.join(', ');
                                                        }
                                                        }
                                                        MenuProps={MenuProps}
                                                    >
                                                        <MenuItem disabled value="">
                                                            <em>Жанры</em>
                                                        </MenuItem>
                                                        {genres.map((name) => (
                                                            <MenuItem key={name.id}
                                                                      value={name.title}>
                                                                <Checkbox
                                                                    checked={postGenresList.indexOf(name.title) > -1}/>
                                                                <ListItemText primary={name.title}/>
                                                            </MenuItem>
                                                        ))}
                                                    </Select>
                                                </FormControl>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className={style.send_new_film__btn}>
                                <button onClick={() => addNewFilm()}>Сохранить</button>
                            </div>
                        </div>
                    </div>
                }
            </div>
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
export default RedactorPage