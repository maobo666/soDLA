package nvdla
import chisel3._
import chisel3.experimental._
import chisel3.util._

class NV_NVDLA_MCIF_read(implicit conf: nocifConfiguration) extends Module {
    val io = IO(new Bundle{
        val reg2dp_rd_os_cnt = Input(UInt(8.W))
        val reg2dp_rd_weight_bdma = Input(UInt(8.W))
        val reg2dp_rd_weight_cdma_dat = Input(UInt(8.W))
        val reg2dp_rd_weight_cdma_wt = Input(UInt(8.W))
        val reg2dp_rd_weight_cdp = Input(UInt(8.W))
        val reg2dp_rd_weight_pdp = Input(UInt(8.W))
        val reg2dp_rd_weight_rbk = Input(UInt(8.W))
        val reg2dp_rd_weight_sdp = Input(UInt(8.W))
        val reg2dp_rd_weight_sdp_b = Input(UInt(8.W))
        val reg2dp_rd_weight_sdp_e = Input(UInt(8.W))
        val reg2dp_rd_weight_sdp_n = Input(UInt(8.W))

        //general clock
        val nvdla_core_clk = Input(Clock())
        val nvdla_core_rstn = Input(Bool())

        val bdma2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val bdma2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val bdma2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val bdma2mcif_rd_req_pd = Input(UInt(79.W))

        val cdma_dat2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val cdma_dat2mcif_rd_req_ready = Output(Bool())   /* data return handshake */
        val cdma_dat2mcif_rd_req_pd = Input(UInt(79.W))

        val cdma_wt2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val cdma_wt2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val cdma_wt2mcif_rd_req_pd = Input(UInt(79.W))

        val cdp2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val cdp2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val cdp2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val cdp2mcif_rd_req_pd = Input(UInt(79.W))

        val pdp2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val pdp2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val pdp2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val pdp2mcif_rd_req_pd = Input(UInt(79.W))

        val rbk2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val rbk2mcif_rd_req_valid = Input(Bool()) /* data valid */
        val rbk2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val rbk2mcif_rd_req_pd = Input(UInt(79.W));

        val sdp2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val sdp2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val sdp2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val sdp2mcif_rd_req_pd = Input(UInt(79.W))

        val sdp_b2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val sdp_b2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val sdp_b2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val sdp_b2mcif_rd_req_pd = Input(UInt(79.W))

        val sdp_e2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val sdp_e2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val sdp_e2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val sdp_e2mcif_rd_req_pd = Input(UInt(79.W))

        val sdp_n2mcif_rd_cdt_lat_fifo_pop = Input(Bool())

        val sdp_n2mcif_rd_req_valid = Input(Bool())  /* data valid */
        val sdp_n2mcif_rd_req_ready = Output(Bool())  /* data return handshake */
        val sdp_n2mcif_rd_req_pd = Input(UInt(79.W))

        val mcif2bdma_rd_rsp_valid = Output(Bool())  /* data valid */
        val mcif2bdma_rd_rsp_ready = Input(Bool()) /* data return handshake */
        val mcif2bdma_rd_rsp_pd = Output(UInt(514.W))

        val mcif2cdma_dat_rd_rsp_valid = Output(Bool())  /* data valid */
        val mcif2cdma_dat_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val cif2cdma_dat_rd_rsp_pd = Output(UInt(514.W))

        val mcif2cdma_wt_rd_rsp_valid = Output(Bool())   /* data valid */
        val mcif2cdma_wt_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2cdma_wt_rd_rsp_pd = Output(UInt(514.W))

        val mcif2cdp_rd_rsp_valid = Output(Bool())   /* data valid */
        val mcif2cdp_rd_rsp_ready = Input(Bool()) /* data return handshake */
        val mcif2cdp_rd_rsp_pd = Output(UInt(514.W))

        val mcif2pdp_rd_rsp_valid = Output(Bool())    /* data valid */
        val mcif2pdp_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2pdp_rd_rsp_pd = Output(UInt(514.W))

        val mcif2rbk_rd_rsp_valid = Output(Bool())    /* data valid */
        val mcif2rbk_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2rbk_rd_rsp_pd = Output(UInt(514.W))

        val mcif2sdp_b_rd_rsp_valid = Output(Bool())    /* data valid */
        val mcif2sdp_b_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2sdp_b_rd_rsp_pd = Output(UInt(514.W))

        val mcif2sdp_e_rd_rsp_valid = Output(Bool())   /* data valid */
        val mcif2sdp_e_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2sdp_e_rd_rsp_pd = Output(UInt(514.W))

        val mcif2sdp_n_rd_rsp_valid = Output(Bool())   /* data valid */
        val mcif2sdp_n_rd_rsp_ready = Input(Bool())  /* data return handshake */
        val mcif2sdp_n_rd_rsp_pd = Output(UInt(514.W))

        val mcif2sdp_rd_rsp_valid = Output(Bool())    /* data valid */
        val mcif2sdp_rd_rsp_ready = Input(Bool()) /* data return handshake */
        val mcif2sdp_rd_rsp_pd = Output(UInt(514.W))

        val pwrbus_ram_pd = Input(UInt(32.W))

        //noc2mcif
        val noc2mcif_axi_r_rvalid = Input(Bool())
        val noc2mcif_axi_r_rready = Output(Bool())
        val noc2mcif_axi_r_rid = Input(UInt(8.W))
        val noc2mcif_axi_r_rlast = Input(Bool())
        val noc2mcif_axi_r_rdata = Input(UInt(512.W))
        
        //mcif2noc
        val mcif2noc_axi_ar_arvalid = Output(Bool())
        val mcif2noc_axi_ar_arready = Input(Bool())
        val mcif2noc_axi_ar_arid = Output(UInt(8.W))
        val mcif2noc_axi_ar_arlen = Output(UInt(4.W))
        val mcif2noc_axi_ar_araddr = Output(UInt(conf.NVDLA_MEM_ADDRESS_WIDTH.W))
    })

    withClock(io.nvdla_core_clk){
    }
}