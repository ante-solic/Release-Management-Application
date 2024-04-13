import React from 'react'

const Header = ({toggleModal, nbOfUsers}) => {
  return (
    <header>
        <div>
            <h3>User List ({nbOfUsers})</h3>
            <button onClick={() => toggleModal(true)}> Add New User</button>
        </div>
    </header>
  )
}

export default Header