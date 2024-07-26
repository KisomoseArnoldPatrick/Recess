@extends('layouts/dash')
@section('content')
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                                    

                                    <div class="page-title-right">
                                       
                                    </div>

                                </div>
                            </div>
                        </div>
                       
        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">CHALLENGES</h4>
                                       
                                        
                                        <div class="table-responsive">
                                           
                                            <table class="table table-hover mb-0">
        
                                                <thead>
                                                    <tr>
                                                        <th>Challenge No</th>
                                                        <th>AttemptDuration</th>
                                                        <th>No of Questions </th>
                                                        <th>OverallMark</th>
                                                        <th>Opendate</th>
                                                        <th>Closedate</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                @foreach( $viewchallenge as $Challenge)
                                                    <tr>
                                                        <td>{{$challenge->challengeNo}}</td>
                                                        <td>{{$challenge->attemptDuration}}</td>
                                                        <td>{{$challenge->noOfQuestions}}</td>
                                                        <td>{{$challenge->overallMark}}</td>
                                                        <td>{{$challenge->openDate}}</td>
                                                        <td>{{$challenge->closeDate}}</td>
                                                    </tr>
                                                    @endforeach
                                                   
                                                </tbody>
                                            </table>
                                        </div>
        
                                    </div>
                                </div>
                            </div>
                            
                            
                        </div>
                        <!-- end row -->
        
                        <div class="row">
                            
                            
                            <div class="col-lg-6">
                                
                            </div>
                        </div>
                        <!-- end row -->
                        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    
                                </div>
                            </div>
                        </div>
                        <!-- end row -->
                        
                  
                
               
                
         

@endsection