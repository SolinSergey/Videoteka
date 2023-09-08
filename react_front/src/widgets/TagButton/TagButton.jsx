import style from './TagButton.module.css';

function TagButton(props){
    return(
        <div className={style.genre}>

            <div className={style.genre_item}><span>{props.genre}</span></div>

        </div>
    )
}

export default TagButton