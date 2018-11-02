import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/product-type">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Product Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/product">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Product
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/partner">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Partner
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/pay-partner">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Pay Partner
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet-transaction-type">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Wallet Transaction Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Wallet
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet-rule">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Wallet Rule
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet-rule-rate">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Wallet Rule Rate
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/wallet-transaction">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Wallet Transaction
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/partner-transaction">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Partner Transaction
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/pay-partner-log">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Pay Partner Log
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/partner-log">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Partner Log
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
