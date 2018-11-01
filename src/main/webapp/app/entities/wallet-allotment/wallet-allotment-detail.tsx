import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-allotment.reducer';
import { IWalletAllotment } from 'app/shared/model/wallet-allotment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletAllotmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletAllotmentDetail extends React.Component<IWalletAllotmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletAllotmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletAllotment [<b>{walletAllotmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletAllotmentAmount">Wallet Allotment Amount</span>
            </dt>
            <dd>{walletAllotmentEntity.walletAllotmentAmount}</dd>
            <dt>Partner</dt>
            <dd>{walletAllotmentEntity.partner ? walletAllotmentEntity.partner.id : ''}</dd>
            <dt>Wallet</dt>
            <dd>{walletAllotmentEntity.wallet ? walletAllotmentEntity.wallet.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-allotment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-allotment/${walletAllotmentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletAllotment }: IRootState) => ({
  walletAllotmentEntity: walletAllotment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletAllotmentDetail);
