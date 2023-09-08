import './CountryListPage.css'
import {MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import axios from "axios";
import {useEffect, useState} from "react";
import ModalWindow from "../../../widgets/ModalWindow/ModalWindow";
import AddCountryPage from "../AddCountryPage/AddCountryPage";

const CountryListPage = (props) => {

   let getCountries = () =>{
        axios.get('http://localhost:5555/catalog/api/v1/country/all')
            .then(r => r.data )
            .then(data => {
                setCountry(data)
            })
    }
    useEffect(() => {
        getCountries()
    }, [])
    const [countries, setCountry] = useState([])
    const [modalActive, setModalActive] = useState(false);
    return(
        <div className={'country_list__container'}>
            <div className={'country_list__title'}>
            Страны
                <button onClick={() => setModalActive(true)}>+ добавить страну</button>
            </div>
            <div className={'user_page__box'}>
                <TableContainer component={Paper}>
                    <Table  stickyHeader={true} sx={{minWidth: 1050}} aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{background: '#2b303b', color: 'white'}}>Название</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="center">Id</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {countries.map((row) => (
                                <TableRow
                                    hover role="checkbox"
                                    tabIndex={-1}
                                    key={row.id}
                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.title}
                                    </TableCell>
                                    <TableCell align="center">{row.id}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
            <ModalWindow active={modalActive}
                         setActive={setModalActive}
            >
                <AddCountryPage getCountries={() => getCountries()}
                                setActive={setModalActive}
                />
            </ModalWindow>
        </div>
    )
}

export default CountryListPage;