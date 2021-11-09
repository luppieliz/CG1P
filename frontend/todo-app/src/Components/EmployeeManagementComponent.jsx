
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Placeholder from 'react-bootstrap/Placeholder';
import React, { Component } from 'react';
import UserDataService from '../api/UserDataService';
import { SESSION_USER_ID, SESSION_USER_NAME } from '../Constants';



class EmployeeManagementComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            employees: [],
        }
    }

    componentDidMount() {
        UserDataService.retrieveUser(sessionStorage.getItem(SESSION_USER_ID)).then(response => {
            UserDataService.retrieveUsersByBusiness(response.data.business.id).then(response => {
                this.setState({employees: response.data})
            })
        });
    }

    render() {
        return (
            <>
            <div >
                <Container>
                <Placeholder xs={12} bg="transparent"  />
                <h1  className="text-dark cg1p-header" >Your Employees</h1>
                <Placeholder xs={12} bg="transparent"  />
                <h3 class="text-dark ">Employees and COVID/SHN Status</h3>
                </Container>
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
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
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                </div>
            </>
        )
    }

    
}

export default EmployeeManagementComponent;