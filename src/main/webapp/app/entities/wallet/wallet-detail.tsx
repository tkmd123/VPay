import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletDetail extends React.Component<IWalletDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Wallet [<b>{walletEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletNumber">Wallet Number</span>
            </dt>
            <dd>{walletEntity.walletNumber}</dd>
            <dt>
              <span id="walletIsActive">Wallet Is Active</span>
            </dt>
            <dd>{walletEntity.walletIsActive ? 'true' : 'false'}</dd>
            <dt>
              <span id="walletDesc">Wallet Desc</span>
            </dt>
            <dd>{walletEntity.walletDesc}</dd>
            <dt>Wallet Type</dt>
            <dd>{walletEntity.walletType ? walletEntity.walletType.id : ''}</dd>
            <dt>Pay Partner</dt>
            <dd>{walletEntity.payPartner ? walletEntity.payPartner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet/${walletEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ wallet }: IRootState) => ({
  walletEntity: wallet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletDetail);
