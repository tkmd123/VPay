import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-type.reducer';
import { IWalletType } from 'app/shared/model/wallet-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTypeDetail extends React.Component<IWalletTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletType [<b>{walletTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletTypeCode">Wallet Type Code</span>
            </dt>
            <dd>{walletTypeEntity.walletTypeCode}</dd>
            <dt>
              <span id="walletTypeName">Wallet Type Name</span>
            </dt>
            <dd>{walletTypeEntity.walletTypeName}</dd>
            <dt>
              <span id="walletTypeDesc">Wallet Type Desc</span>
            </dt>
            <dd>{walletTypeEntity.walletTypeDesc}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-type/${walletTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletType }: IRootState) => ({
  walletTypeEntity: walletType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTypeDetail);
