import "./CatalogPage.module.css";
import style from "./CatalogPage.module.css";
import {
    Button,
    ButtonGroup,
    CircularProgress,
    FormControl,
    InputLabel,
    MenuItem,
    Pagination,
    Select
} from "@mui/material";
import FilmCard from "../../../widgets/FilmCard/FilmCard";
import axios from "axios";
import React, {Component, useEffect, useRef} from "react";
import SearchBar from "../../../widgets/SearchBar/SearchBar";
import {toast, ToastContainer} from "react-toastify";
import {Route, Routes} from "react-router-dom";
import MainPage from "../MainPage";
import EmptyCard from "../../../widgets/EmptyCard/EmptyCard";

class CatalogPage extends Component {
    constructor(props) {
        super(props);
        this.handleDirectorsChange = this.handleDirectorsChange.bind(this);
        this.handleCountriesChange = this.handleCountriesChange.bind(this);
        this.handleSaleSet = this.handleSaleSet.bind(this);
        this.handleRentSet = this.handleRentSet.bind(this);

        this.state = {
            films: [],
            genres: [],
            directors: [],
            countries: [],
            filterCountryList: '',
            filterDirectorList: '',
            filterGenreList: '',
            startPremierYear: '',
            endPremierYear: '',
            minYear: '',
            maxYear: '',
            startPlaceholderYear: '',
            endPlaceholderYear: '',
            titlePart: '',
            isSale: true,
            minPrice: '',
            maxPrice: '',
            findString: '',
            currentPage: 1,
            active: false,
            modal: false,
            minPlaceholderPrice: '',
            maxPlaceholderPrice: '',
        }
    }

    componentDidMount() {
        this.searchHandler()
        this.getMinMaxPrice()
        this.getMinMaxYear()
        this.getAllGenres()
        this.getAllDirectors()
        this.getAllCountries()
        const customer = this.getCurrentUser()
    }

    getCurrentUser = () => {
        return JSON.parse(localStorage.getItem('customer'))
    }
    searchHandler = () => {
        this.setState({
            films: this.props.films,
            totalPages: this.props.totalPages,
            totalElements: this.props.totalElements,
            currentPage: this.props.currentPage
        })
    }

    getMinMaxPrice = () => {
        axios.get("http://localhost:5555/catalog/api/v1/price/prices_filter")
            .then(response => response.data)
            .then((data) => {
                if (this.state.isSale === true) {
                    console.log(data.minPriceSale)
                    this.setState({
                            minPrice: data.minPriceSale,
                            maxPrice: data.maxPriceSale,
                            minPlaceholderPrice: data.minPriceSale,
                            maxPlaceholderPrice: data.maxPriceSale,
                        },
                        () => this.getAllFilms(this.state.currentPage,
                            this.state.filterCountryList,
                            this.state.filterDirectorList,
                            this.state.filterGenreList,
                            this.state.startPremierYear,
                            this.state.endPremierYear,
                            this.state.isSale,
                            this.state.minPrice,
                            this.state.maxPrice
                        )
                    )
                } else {
                    if (this.state.isSale === false) {
                        console.log(data)
                        this.setState({
                            minPrice: data.minPriceRent,
                            maxPrice: data.maxPriceRent,
                            minPlaceholderPrice: data.minPriceRent,
                            maxPlaceholderPrice: data.maxPriceRent,
                        }, () => this.getAllFilms(this.state.currentPage,
                            this.state.filterCountryList,
                            this.state.filterDirectorList,
                            this.state.filterGenreList,
                            this.state.startPremierYear,
                            this.state.endPremierYear,
                            this.state.isSale,
                            this.state.minPrice,
                            this.state.maxPrice
                        ))
                    }
                }

            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getMinMaxYear = () => {
        axios.get("http://localhost:5555/catalog/api/v1/film/min_max_year")
            .then(response => response.data)
            .then((data) => {

                console.log(data.minYear)
                this.setState({
                        startPremierYear: data.minYear,
                        endPremierYear: data.maxYear,
                        startPlaceholderYear: data.minYear,
                        endPlaceholderYear: data.maxYear
                    },
                )
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllGenres = () => {
        axios.get("http://localhost:5555/catalog/api/v1/genre/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    genres: data

                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllDirectors = () => {
        axios.get("http://localhost:5555/catalog/api/v1/director/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    directors: data

                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllCountries = () => {
        axios.get("http://localhost:5555/catalog/api/v1/country/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    countries: data

                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllFilms = (currentPage,
                   filterCountryList,
                   filterDirectorList,
                   filterGenreList,
                   startPremierYear,
                   endPremierYear,
                   isSale,
                   minPrice,
                   maxPrice,
                   findString
    ) => {
        currentPage -= 1;
        axios.get("http://localhost:5555/catalog/api/v1/film/all_with_filter/",
            {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                params: {
                    currentPage,
                    filterCountryList,
                    filterDirectorList,
                    filterGenreList,
                    startPremierYear,
                    endPremierYear,
                    isSale,
                    minPrice,
                    maxPrice,
                    findString,
                }
            })
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    films: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                })
            }, function errorCallback(response) {
                console.log(response)
                // if (response.response.data.code === "NO_DATA"){
                //     window.location = 'empty'
                // }
                let displayNotification = (message) => {
                    toast.error(message);
                }
                displayNotification(response.response.data.value)
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }

    clearState() {
        this.setState(
            {
                filterCountryList: '',
                filterDirectorList: '',
                filterGenreList: '',
                startPremierYear: '',
                endPremierYear: '',
                isSale: true,
                minPrice: '',
                maxPrice: '',
                findString: '',
                currentPage: 1,
                active: false
            }, () => {
                // const filterInput = useRef()
                // filterInput.current.value = ''
                this.getMinMaxPrice();
                this.getMinMaxYear();
            }
        )
    }


    filmFilterByGenres(genre) {
        if (genre === "Все") {
            this.clearState()
        } else {
            this.setState(
                {
                    filterGenreList: genre
                }, () => this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice,
                    this.state.findString
                )
            )
        }
    }

    handleDirectorsChange(event) {
        console.log(event.target.value)
        if (event.target.value === "Все") {
            this.clearState()
        } else {
            if (event.target.value !== "Все") {
                this.setState(
                    {
                        filterDirectorList: event.target.value
                    }, () => this.getAllFilms(this.state.currentPage,
                        this.state.filterCountryList,
                        this.state.filterDirectorList,
                        this.state.filterGenreList,
                        this.state.startPremierYear,
                        this.state.endPremierYear,
                        this.state.isSale,
                        this.state.minPrice,
                        this.state.maxPrice,
                        this.state.findString)
                )
            }
        }
    }

    usePageHandler(num) {
        console.log(num)
        this.setState(
            {
                currentPage: num
            }, () => this.getAllFilms(this.state.currentPage,
                this.state.filterCountryList,
                this.state.filterDirectorList,
                this.state.filterGenreList,
                this.state.startPremierYear,
                this.state.endPremierYear,
                this.state.isSale,
                this.state.minPrice,
                this.state.maxPrice,
                this.state.findString)
        )
    }

    handleCountriesChange(event) {
        this.setState(
            {
                filterCountryList: event.target.value
            }, () => this.getAllFilms(this.state.currentPage,
                this.state.filterCountryList,
                this.state.filterDirectorList,
                this.state.filterGenreList,
                this.state.startPremierYear,
                this.state.endPremierYear,
                this.state.isSale,
                this.state.minPrice,
                this.state.maxPrice,
                this.state.findString
            )
        )
    }


    handleDateChange(name, event) {
        let value = event.target.value;
        if (name === "second") {
            if (parseInt(this.state.startPlaceholderYear) <= parseInt(value) && value.length === 4) {
                this.setState({endPremierYear: value}, () => this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice,
                    this.state.findString));
            }
        } else {
            if (parseInt(value) <= parseInt(this.state.endPlaceholderYear) && value.length === 4) {
                this.setState({startPremierYear: value}, () => this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice,
                    this.state.findString));

            }
        }
    }


    handleSaleSet() {
        this.setState({isSale: true}, () => this.getMinMaxPrice())
    }


    handleRentSet() {
        this.setState({isSale: false}, () => this.getMinMaxPrice())
    }

    handlePriceChange(name, event) {
        let value = event.target.value;
        if (name === "second") {
            if (parseInt(this.state.minPlaceholderPrice) <= parseInt(value)) {
                this.setState({maxPrice: value}, () => this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice,
                    this.state.findString));
            }
        } else {
            if (parseInt(value) <= parseInt(this.state.maxPlaceholderPrice) && parseInt(value) >= parseInt(this.state.minPlaceholderPrice)) {
                this.setState({minPrice: value}, () => this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice,
                    this.state.findString));

            }
        }
    }

    getFilmByTitlePart = (value) => {
        console.log(value)
        this.setState({findString: value}, () => this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice,
            this.state.findString));

    }

    render() {
        const {films, currentPage, filmsPerPage} = this.state;
        const genres = this.state.genres;
        const directors = this.state.directors;
        const countries = this.state.countries;
        const totalPages = this.state.totalPages;
        const {active, setActive} = this.state.active;
        const role = JSON.parse(localStorage.getItem('role_user'));

        return (
            <div className={style.catalog_container}>

                <div className={style.genre_bar}>
                    <ButtonGroup variant="text" size="small" aria-label="outlined primary button group">
                        <Button onClick={() => this.filmFilterByGenres("Все")} className={style.unselected}>Все</Button>
                        {genres.map((genre) =>
                            <Button onClick={() => this.filmFilterByGenres(genre.title)}
                                    className={active ? style.selected : style.unselected}>{genre.title}</Button>
                        )}
                    </ButtonGroup>

                </div>

                <SearchBar getFilmByTitlePart={(value) => this.getFilmByTitlePart(value)}/>

                <div className={style.pagination}>
                    {
                        films.length > 0 ?
                            <div className={style.catalog_menu}>
                                {/*<div className={style.current_pages}>*/}
                                {/*    <h4>Это {currentPage} страница из {totalPages}</h4>*/}
                                {/*</div>*/}

                                <div className={style.pagination_items}>
                                    <Pagination count={totalPages}
                                                page={currentPage}
                                                color="success"
                                                siblingCount={1}
                                                sx={{backgroundColor: "#414141"}}
                                                onChange={(_, num) => this.usePageHandler(num)}
                                    />
                                </div>
                            </div>
                            :
                            <div className={style.empty}>
                            </div>
                    }
                </div>

                <div className={style.catalog}>
                    {/*<Routes>*/}
                    {/*    <Route path={'empty'} element={<EmptyCard/>}/>*/}
                    {/*</Routes>*/}
                    {
                        films.length === 0 ?
                            <div className={style.empty}>
                                <CircularProgress color="secondary"
                                                  sx={{mt: 25, ml: 70}}
                                />
                            </div> :
                            films.map((film) => (
                                <FilmCard imageUrlLink={film.imageUrlLink}
                                          id={film.id}
                                          isSale={this.state.isSale}
                                          salePrice={film.salePrice}
                                          rentPrice={film.rentPrice}
                                          title={film.title}
                                          premierYear={film.premierYear}
                                          country={film.country}
                                          genre={film.genre}
                                          director={film.director}
                                          description={film.description}
                                />
                            ))
                    }

                </div>

                <div className={style.filter_card}>

                    <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                        <InputLabel id="demo-select-small-label">Режиссёр</InputLabel>
                        <Select
                            labelId="demo-select-small-label"
                            id="demo-select-small"
                            name={'director'}
                            value={this.state.filterDirectorList}
                            color={'success'}
                            defaultValue={''}
                            sx={{backgroundColor: 'darkgrey', borderColor: "success"}}
                            label="Age"
                            onChange={this.handleDirectorsChange}
                        >
                            <MenuItem value="Все">
                                <em>Все</em>
                            </MenuItem>
                            {directors.map((director) =>
                                <MenuItem
                                    value={director.firstName + " " + director.lastName}>{director.firstName} {director.lastName}</MenuItem>
                            )}
                        </Select>
                    </FormControl>
                    <div>
                        <FormControl sx={{m: 1, minWidth: 120}} size="small">
                            <InputLabel id="demo-select-small-label">Страна</InputLabel>
                            <Select
                                labelId="country"
                                id="demo-select-small"
                                name={'country'}
                                value={this.state.filterCountryList}
                                color={'success'}
                                defaultValue={'Все'}
                                sx={{backgroundColor: 'darkgrey', borderColor: "success"}}
                                label="Страна"
                                onChange={this.handleCountriesChange}
                            >
                                <MenuItem value={''} onClick={this.handleClear}>
                                    <em>Все</em>
                                </MenuItem>
                                {countries.map((country) =>
                                    <MenuItem value={country.title}>{country.title}</MenuItem>
                                )}
                            </Select>
                        </FormControl>
                    </div>
                    <br/>
                    <span className={style.filter_title}>Годы премьер</span>
                    <div>
                        <div className={style.year_range}>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.start__field}
                                       placeholder={this.state.startPlaceholderYear}
                                       onChange={this.handleDateChange.bind(this, "first")}/>
                            </div>
                            <div className={style.separator}><span>-</span></div>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.end__field}
                                       placeholder={this.state.endPlaceholderYear}
                                       onChange={this.handleDateChange.bind(this, "second")}/>
                            </div>
                        </div>
                    </div>
                    <br/>
                    {this.state.isSale ?
                        <span className={style.filter_title}>Диапазон цен продажи</span>
                        :
                        <span className={style.filter_title}>Диапазон цен аренды</span>
                    }

                    <div>
                        <div className={style.year_range}>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.start__field}
                                       placeholder={this.state.minPlaceholderPrice}
                                       onChange={this.handlePriceChange.bind(this, "first")}/>
                            </div>
                            <div className={style.separator}><span>-</span></div>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.end__field}
                                       placeholder={this.state.maxPlaceholderPrice}
                                       onChange={this.handlePriceChange.bind(this, "second")}/>
                            </div>
                        </div>
                    </div>
                    {!this.state.isSale ?
                        <Button onClick={this.handleSaleSet} className={style.filter_btn}>Продажа</Button>
                        :
                        <Button onClick={this.handleRentSet} className={style.filter_btn}>Аренда</Button>
                    }
                    <Button onClick={() => this.filmFilterByGenres("Все")}
                            className={style.filter_btn}>Сбросить</Button>

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
}

export default CatalogPage;