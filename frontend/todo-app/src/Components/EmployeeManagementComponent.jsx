import Container from 'react-bootstrap/Container';
import Placeholder from 'react-bootstrap/Placeholder';
import React, { Component } from 'react';
import UserDataService from '../api/UserDataService';
import { SESSION_USER_BUSINESS } from '../Constants';
import { SESSION_USER_ROLE } from '../Constants';

class EmployeeManagementComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            employees: [],
        }
    }

    componentDidMount() {
        console.log(sessionStorage.getItem(SESSION_USER_BUSINESS))
        UserDataService.retrieveUsersByBusiness(sessionStorage.getItem(SESSION_USER_BUSINESS))
            .then(response => {
                this.setState({ employees: response.data })
            });
    }

    render() {

        

        if (sessionStorage.getItem(SESSION_USER_ROLE) == "ROLE_EMPLOYEE") {

            return (
                <Container>
                    <Placeholder xs={12} bg="transparent" />
                    <h1 className="text-dark cg1p-header" >You are not authorized to access this</h1>
                    <Placeholder xs={12} bg="transparent" style={{paddingBottom: "70vh"}}/>
                </Container>
            )

        } else {


            return (
                <div >
                    <Container>
                        <Placeholder xs={12} bg="transparent" />
                        <h1 className="text-dark cg1p-header" >Your Employees</h1>
                        <Placeholder xs={12} bg="transparent" />
                        <h3 class="text-dark ">Employees and COVID/SHN Status</h3>
                    </Container>
                    <Placeholder xs={12} bg="transparent" />
                    <Placeholder xs={12} bg="transparent" />
                    <Container>
                        <table className="table text-dark">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Has Covid</th>
                                    <th>Has SHN</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.employees.map(
                                        employee =>
                                            <tr key={employee.id}>
                                                <td>{employee.name}</td>
                                                <td>{employee.email}</td>
                                                <td>{employee.covidStatus.toString()}</td>
                                                <td>{employee.shnStatus.toString()}</td>
                                            </tr>
                                    )
                                }
                            </tbody>
                        </table>
                    </Container>
                    <Placeholder xs={12} bg="transparent" style={{ paddingBottom: "50vh" }} />
                </div>
            )
        }
    }
}

export default EmployeeManagementComponent;