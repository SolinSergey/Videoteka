import './DirectorsListPage.css'
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import ModalWindow from "../../../widgets/ModalWindow/ModalWindow";
import AddCountryPage from "../AddCountryPage/AddCountryPage";
import axios from "axios";
import {useEffect, useState} from "react";
import AddDirectorPage from "../AddDirectorPage/AddDirectorPage";

const DirectorsListPage = () => {
    let getDirectors = () =>{
        axios.get('http://localhost:5555/catalog/api/v1/director/all')
            .then(r => r.data )
            .then(data => {
                setDirectors(data)
            })
    }
    useEffect(() => {
        getDirectors()
    }, [])
    const [directors, setDirectors] = useState([])
    const [modalActive, setModalActive] = useState(false);
    return(
        <div className={'directors_list__container'}>
            <div className={'directors_list__title'}>
                Режиссёры
                <button onClick={() => setModalActive(true)}>+ добавить режиссёра</button>
            </div>
            <div className={'user_page__box'}>
                <TableContainer component={Paper}>
                    <Table stickyHeader={true} sx={{minWidth: 1050}} aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{background: '#2b303b', color: 'white'}}>Имя</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="center">Id</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {directors.map((row) => (
                                <TableRow
                                    hover role="checkbox"
                                    tabIndex={-1}
                                    key={row.id}
                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.firstName + ' ' + row.lastName}
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
                <AddDirectorPage getDirectors={() => getDirectors()}
                                 setActive={setModalActive}
                />
            </ModalWindow>
        </div>
    )
}

export default DirectorsListPage