import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";

export default function RoleComponent(props) {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <div>
            <Button
                variant="contained"
                size="large"
                aria-controls="customized-menu"
                aria-haspopup="true"
                onClick={handleClick}>
                MENU
            </Button>
            <Menu
                id="customized-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
                transformOrigin={{vertical: "top", horizontal: "center"}}>
                {props.createLoading ? <CircularProgress /> : <MenuItem aria-controls="customized-menu"
                          onClick={() => {
                              props.createTestPoints();
                              handleClose()
                          }}>
                    <Typography className='account-menu-item-typography' variant='body1' align='center'>
                        CREATE POINTS
                    </Typography>
                </MenuItem>}
                {props.removeLoading ? <CircularProgress /> : <MenuItem aria-controls="customized-menu"
                          onClick={() => {
                              props.deleteTestPoints();
                              handleClose()
                          }}>
                    <Typography className='account-menu-item-typography' variant='body1' align='center'>
                        REMOVE POINTS
                    </Typography>
                </MenuItem>}
            </Menu>
        </div>
    );
}