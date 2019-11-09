package com.company.gamestoreservice.ControllerTest;

//
//@RunWith(SpringRunner.class)
//@WebMvcTest(InvoiceController.class)
//public class InvoiceControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private GameStoreServiceLayer repo;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @Before
//    public void setUp() {
//
//
//    }
//    @Test
//    public void createInvoices() throws Exception{
//        InvoiceViewModel invoice = new InvoiceViewModel();
//        invoice.setSubTotal(BigDecimal.valueOf(200));
//        invoice.setZipCode("30039");
//        invoice.setCity("snellville");
//        invoice.setItemId(1);
//        invoice.setItemType("test");
//        invoice.setName("akshay");
//        invoice.setProcessingFee(BigDecimal.valueOf(205));
//        invoice.setState("ga");
//        invoice.setQuantity(2);
//        invoice.setTotal(BigDecimal.valueOf(200));
//        invoice.setTax(BigDecimal.valueOf(100));
//        invoice.setUnitPrice(BigDecimal.valueOf(200));
//
//        String inputJson = mapper.writeValueAsString(invoice);
//
//        InvoiceViewModel outputviewmodel = new InvoiceViewModel();
//        outputviewmodel.setInvoiceId(1);
//        outputviewmodel.setSubTotal(BigDecimal.valueOf(200));
//        outputviewmodel.setZipCode("30039");
//        outputviewmodel.setCity("snellville");
//        outputviewmodel.setItemId(1);
//        outputviewmodel.setItemType("test");
//        outputviewmodel.setName("akshay");
//        outputviewmodel.setProcessingFee(BigDecimal.valueOf(205));
//        outputviewmodel.setState("ga");
//        outputviewmodel.setQuantity(2);
//        outputviewmodel.setTotal(BigDecimal.valueOf(200));
//        outputviewmodel.setTax(BigDecimal.valueOf(100));
//        outputviewmodel.setUnitPrice(BigDecimal.valueOf(200));
//
//        String outputJson = mapper.writeValueAsString(outputviewmodel);
//
//        when(repo.saveInvoice(invoice)).thenReturn(outputviewmodel);
//
//        this.mockMvc.perform(post("/order")
//                .content(inputJson)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().json(outputJson));
//
//    }
//}