import * as React from 'react'
import Dialog from '@mui/material/Dialog'
import Slide from '@mui/material/Slide'
import {Box, useMediaQuery} from "@mui/material"

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction='left' ref={ref} {...props}  />
})
export default function AlertDialogSlide( {setCommand, open, setOpen, children} ) {
    const handleClose = () => {
        setOpen(false)
        setCommand('')
    }
    const isNonMobileScreens = useMediaQuery("(min-width: 1000px)")
    return (
        <Box width={isNonMobileScreens ? "60%" : "93%"} p='2rem'>
            <Dialog
                open={open}
                TransitionComponent={Transition}
                keepMounted
                onClose={handleClose}
                aria-describedby='alert-dialog-slide-description'
            >
                {children}
            </Dialog>
        </Box>
    )
}