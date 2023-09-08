import {Box, Typography} from "@mui/material"
import {JSX} from "react"
import MailPage from "../components/LoginPage/MailPage/MailPage"

export const determineModalContent = (step: number): JSX.Element => {
    switch (step) {
    case 1:
        return <MailPage step={step}/>
    case 2:
        return <Box
            display='flex'
            justifyContent='center'
            p='1rem'
            alignItems={'center'}
            textAlign={'center'}
        >
            <Typography fontWeight='500' variant='h5'>Второй шаг регистрации</Typography>
        </Box>
    case 3:
        return <Box display='flex'
            justifyContent='center'
            p='1rem'
            alignItems={'center'}
            textAlign={'center'}
        >
            <Typography fontWeight='500' variant='h5'>Третий шаг регистрации</Typography>
        </Box>
    case 4:
        return <Box display='flex'
            justifyContent='center'
            p='1rem'
            alignItems={'center'}
            textAlign={'center'}
        >
            <Typography fontWeight='500' variant='h5'>Четвёртый шаг регистрации</Typography>
        </Box>
    case 5:
        return <Box display='flex'
            justifyContent='center'
            p='1rem'
            alignItems={'center'}
            textAlign={'center'}>
            <Typography fontWeight='500' variant='h5'>Пятый шаг регистрации</Typography>
        </Box>
    case 6:
        return <Box display='flex'
            justifyContent='center'
            p='1rem'
            width={'64px'}>
            <Typography fontWeight='500' variant='h5'>Последний шаг регистрации</Typography>
        </Box>
    default:
        return <></>

    }
}