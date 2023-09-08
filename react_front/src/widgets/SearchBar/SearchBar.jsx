import "./SearchBar.css"
import SearchIcon from '@mui/icons-material/Search';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useRef, useState} from "react";

function SearchBar(props) {

    let onChange = (event) => {
        console.log(event.target.value)
        setTitlePart(event.target.value)
        setActive(true)
    }
    let handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            console.log("Клавиша нажата")
            sendTitlePart()
        }
    }

    function sendTitlePart() {
        props.getFilmByTitlePart(titlePart)
    }

    const [titlePart, setTitlePart] = useState('')
    const [active, setActive] = useState(false)
    const refInput = useRef()

    function clearInput() {
        refInput.current.value = ''
        props.getFilmByTitlePart('')
        setActive(false)
    }

    return (
        <div className={'search_container'}>
            <div className="box">
                <div className="container-1">
                    <input type="text" id="search" placeholder="Поиск..." ref={refInput}
                           onChange={(event) => onChange(event)}
                           onKeyDown={handleKeyPress}
                    />
                    {active ?
                        <button className={'back_btn'} onClick={() => clearInput()}><ArrowBackIcon/></button>
                        :
                        null
                    }
                    <span className="icon"><button onClick={() => sendTitlePart()} className="fa fa-search"><SearchIcon/></button></span>
                </div>
            </div>
        </div>
    )
}

export default SearchBar;