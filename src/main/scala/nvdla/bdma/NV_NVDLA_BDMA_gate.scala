package nvdla

import chisel3._
import chisel3.experimental._

class NV_NVDLA_BDMA_gate extends Module {
   val io = IO(new Bundle {
        //in clock
        val dla_clk_ovr_on_sync = Input(Clock())
        val global_clk_ovr_on_sync = Input(Clock())
        val nvdla_core_clk = Input(Clock())

        //enable
        val csb2gate_slcg_en= Input(Bool())
        val ld2gate_slcg_en = Input(Bool())
        val st2gate_slcg_en = Input(Bool())    
        val tmc2slcg_disable_clock_gating = Input(Bool())

        //out clock
        val nvdla_gated_clk = Output(Clock())

    })
    val slcg_en = io.csb2gate_slcg_en | io.ld2gate_slcg_en | io.st2gate_slcg_en
    val nvdla_core_clk_slcg_0_en = slcg_en | io.dla_clk_ovr_on_sync.asUInt.toBool |
                                   (io.tmc2slcg_disable_clock_gating|io.global_clk_ovr_on_sync.asUInt.toBool)

    val nvdla_core_clk_slcg_0 = Module(new NV_CLK_gate_power())
    nvdla_core_clk_slcg_0.io.clk := io.nvdla_core_clk
    nvdla_core_clk_slcg_0.io.clk_en := nvdla_core_clk_slcg_0_en
    io.nvdla_gated_clk := nvdla_core_clk_slcg_0.io.clk_gated

}

object NV_NVDLA_BDMA_gateDriver extends App {
  chisel3.Driver.execute(args, () => new NV_NVDLA_BDMA_gate)
}